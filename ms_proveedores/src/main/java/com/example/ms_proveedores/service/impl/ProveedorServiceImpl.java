package com.example.ms_proveedores.service.impl;

import com.example.ms_proveedores.dto.ProveedorDto;
import com.example.ms_proveedores.dto.SunatResponseDto;
import com.example.ms_proveedores.entity.Proveedor;
import com.example.ms_proveedores.feign.SunatClient;
import com.example.ms_proveedores.repository.ProveedorRepository;
import com.example.ms_proveedores.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProveedorServiceImpl implements ProveedorService {
    private final ProveedorRepository repository;
    private final SunatClient sunatClient;

    private ProveedorDto mapToDto(Proveedor entidad) {
        ProveedorDto dto = new ProveedorDto();
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());
        dto.setRuc(entidad.getRuc());
        dto.setTelefono(entidad.getTelefono());
        dto.setDireccion(entidad.getDireccion());
        dto.setCorreo(entidad.getCorreo());
        return dto;
    }

    private Proveedor mapToEntity(ProveedorDto dto) {
        Proveedor entidad = new Proveedor();
        entidad.setId(dto.getId());
        entidad.setNombre(dto.getNombre());
        entidad.setRuc(dto.getRuc());
        entidad.setTelefono(dto.getTelefono());
        entidad.setDireccion(dto.getDireccion());
        entidad.setCorreo(dto.getCorreo());
        return entidad;
    }

    @Override
    public ProveedorDto crearProveedor(ProveedorDto proveedorDto) {
        // Validar RUC con SUNAT
        SunatResponseDto sunatInfo = sunatClient.validarRuc(proveedorDto.getRuc());
        if (sunatInfo == null || sunatInfo.getRazonSocial() == null) {
            throw new RuntimeException("RUC inválido o no encontrado en SUNAT");
        }
        // Si no se envió nombre, asignar razón social de SUNAT
        if (proveedorDto.getNombre() == null || proveedorDto.getNombre().isBlank()) {
            proveedorDto.setNombre(sunatInfo.getRazonSocial());
        }
        // Verificar duplicados por RUC
        repository.findByRuc(proveedorDto.getRuc()).ifPresent(p -> {
            throw new RuntimeException("Proveedor con RUC ya existe");
        });
        Proveedor entidad = mapToEntity(proveedorDto);
        Proveedor guardado = repository.save(entidad);
        return mapToDto(guardado);
    }

    @Override
    public ProveedorDto obtenerProveedorPorId(Long id) {
        Proveedor entidad = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        return mapToDto(entidad);
    }

    @Override
    public List<ProveedorDto> listarProveedores() {
        return repository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ProveedorDto actualizarProveedor(Long id, ProveedorDto proveedorDto) {
        Proveedor existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        // Si cambió el RUC o no se envió nombre, validar en SUNAT
        if (proveedorDto.getRuc() != null && !proveedorDto.getRuc().equals(existente.getRuc())) {
            SunatResponseDto sunatInfo = sunatClient.validarRuc(proveedorDto.getRuc());
            if (sunatInfo == null || sunatInfo.getRazonSocial() == null) {
                throw new RuntimeException("RUC inválido o no encontrado en SUNAT");
            }
            existente.setRuc(proveedorDto.getRuc());
            if (proveedorDto.getNombre() == null || proveedorDto.getNombre().isBlank()) {
                existente.setNombre(sunatInfo.getRazonSocial());
            } else {
                existente.setNombre(proveedorDto.getNombre());
            }
        } else {
            // Si no cambió RUC, pero se envió nombre, actualizarlo
            if (proveedorDto.getNombre() != null && !proveedorDto.getNombre().isBlank()) {
                existente.setNombre(proveedorDto.getNombre());
            }
        }
        // Actualizar otros campos
        if (proveedorDto.getTelefono() != null) existente.setTelefono(proveedorDto.getTelefono());
        if (proveedorDto.getDireccion() != null) existente.setDireccion(proveedorDto.getDireccion());
        if (proveedorDto.getCorreo() != null) existente.setCorreo(proveedorDto.getCorreo());
        Proveedor actualizado = repository.save(existente);
        return mapToDto(actualizado);
    }

    @Override
    public void eliminarProveedor(Long id) {
        Proveedor entidad = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        repository.delete(entidad);
    }

    @Override
    public SunatResponseDto validarRucConSunat(String ruc) {
        return sunatClient.validarRuc(ruc);
    }
}
