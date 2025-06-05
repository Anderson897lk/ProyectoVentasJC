package com.example.ms_proveedores.service;

import com.example.ms_proveedores.dto.ProveedorDto;
import com.example.ms_proveedores.dto.SunatResponseDto;

import java.util.List;

public interface ProveedorService {
    ProveedorDto crearProveedor(ProveedorDto proveedorDto);
    ProveedorDto obtenerProveedorPorId(Long id);
    List<ProveedorDto> listarProveedores();
    ProveedorDto actualizarProveedor(Long id, ProveedorDto proveedorDto);
    void eliminarProveedor(Long id);
    SunatResponseDto validarRucConSunat(String ruc);
}