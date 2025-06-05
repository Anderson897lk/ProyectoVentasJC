package com.example.ms_proveedores.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProveedorDto {
    private Long id;

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    private String nombre;

    @NotBlank(message = "El RUC es obligatorio")
    private String ruc;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    private String direccion;
    private String correo;
}
