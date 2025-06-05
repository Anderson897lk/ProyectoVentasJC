package com.example.ms_compra.service.impl;

import com.example.ms_compra.dto.CompraDto;
import com.example.ms_compra.entity.Compra;
import com.example.ms_compra.repository.CompraRepository;
import com.example.ms_compra.service.CompraService;
import com.example.ms_compra.feign.ProductoClient;
import com.example.ms_compra.feign.InventarioClient;
import com.example.ms_compra.feign.ProveedorClient;
import com.example.ms_compra.dto.ProveedorDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CompraServiceImpl implements CompraService {
    private final CompraRepository compraRepository;
    private final ProductoClient productoClient;
    private final InventarioClient inventarioClient;
    private final ProveedorClient proveedorClient;

    @Override
    public CompraDto crearCompra(CompraDto compraDto) {
        // 1. Validar existencia de proveedor
        ProveedorDto proveedor;
        try {
            proveedor = proveedorClient.obtenerProveedor(compraDto.getProveedorId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Proveedor no encontrado con id: " + compraDto.getProveedorId());
        }

        // 2. Asignar fecha actual si no viene
        if (compraDto.getFechaCompra() == null) {
            compraDto.setFechaCompra(LocalDate.now());
        }

        // 3. Guardar entidad Compra
        Compra entidad = Compra.builder()
                .productoId(compraDto.getProductoId())
                .cantidad(compraDto.getCantidad())
                .precioCompra(compraDto.getPrecioCompra())
                .precioVenta(compraDto.getPrecioVenta())
                .proveedorId(compraDto.getProveedorId())
                .fechaCompra(compraDto.getFechaCompra())
                .build();

        Compra guardada = compraRepository.save(entidad);

        // 4. Actualizar stock en microservicio Inventario
        inventarioClient.registrarMovimiento(
                guardada.getProductoId(),
                guardada.getCantidad(),
                "ENTRADA"
        );

        // 5. Actualizar precio de venta en microservicio Producto (si cambia)
        productoClient.actualizarPrecio(
                guardada.getProductoId(),
                guardada.getPrecioVenta()
        );

        return mapToDto(guardada);
    }

    @Override
    public CompraDto obtenerCompra(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada con id: " + id));
        return mapToDto(compra);
    }

    @Override
    public List<CompraDto> listarCompras() {
        return compraRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompraDto actualizarCompra(Long id, CompraDto compraDto) {
        Compra existente = compraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada con id: " + id));

        // Actualizar solo campos permitidos
        existente.setCantidad(compraDto.getCantidad());
        existente.setPrecioCompra(compraDto.getPrecioCompra());
        existente.setPrecioVenta(compraDto.getPrecioVenta());
        existente.setFechaCompra(
                compraDto.getFechaCompra() != null ? compraDto.getFechaCompra() : existente.getFechaCompra()
        );

        Compra actualizado = compraRepository.save(existente);

        // Re-ajustar stock: primero restar el stock anterior, luego sumar el nuevo
        int diffCantidad = actualizado.getCantidad() - existente.getCantidad();
        if (diffCantidad != 0) {
            inventarioClient.registrarMovimiento(
                    actualizado.getProductoId(),
                    diffCantidad,
                    diffCantidad > 0 ? "ENTRADA" : "SALIDA"
            );
        }

        // Actualizar precio en Productos si cambió
        productoClient.actualizarPrecio(
                actualizado.getProductoId(),
                actualizado.getPrecioVenta()
        );

        return mapToDto(actualizado);
    }

    @Override
    public void eliminarCompra(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada con id: " + id));

        // Al eliminar, restar stock en Inventario
        inventarioClient.registrarMovimiento(
                compra.getProductoId(),
                compra.getCantidad(),
                "SALIDA"
        );

        compraRepository.deleteById(id);
    }

    private CompraDto mapToDto(Compra entidad) {
        return CompraDto.builder()
                .id(entidad.getId())
                .productoId(entidad.getProductoId())
                .cantidad(entidad.getCantidad())
                .precioCompra(entidad.getPrecioCompra())
                .precioVenta(entidad.getPrecioVenta())
                .proveedorId(entidad.getProveedorId())
                .fechaCompra(entidad.getFechaCompra())
                .build();
    }
}