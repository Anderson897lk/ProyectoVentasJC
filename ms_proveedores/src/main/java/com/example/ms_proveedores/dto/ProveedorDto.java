package com.example.ms_proveedores.dto;

import lombok.Data;

@Data
public class ProveedorDto {
    private Long id;
    private String nombre;  // Permitir nulo o cadena vacía
    private String ruc;
    private String telefono;
    private String direccion;
    private String correo;
}