package com.example.ms_proveedor.service;
import com.example.ms_proveedor.dto.ProveedorDto;
import java.util.List;

public interface ProveedorService {
    ProveedorDto crearCliente(ProveedorDto proveedorDto);
    ProveedorDto obtenerCliente(Long id);
    List<ProveedorDto> listarClientes();
    ProveedorDto actualizarCliente(Long id, ProveedorDto proveedorDto);
    void eliminarCliente(Long id);
}