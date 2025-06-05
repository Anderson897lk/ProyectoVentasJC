package com.example.ms_proveedores.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proveedores")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String ruc;

    @Column(nullable = false)
    private String telefono;

    private String direccion;
    private String correo;
}