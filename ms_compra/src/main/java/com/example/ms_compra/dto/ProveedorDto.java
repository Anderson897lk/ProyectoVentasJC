package com.example.ms_compra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorDto {
    private Long id;
    private String dniOrRuc;
    private String razonSocialONombre;
    private String direccion;
    private String telefono;
}
