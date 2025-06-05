package com.example.ms_proveedor.controller;


import com.example.ms_proveedor.dto.ProveedorDto;
import com.example.ms_proveedor.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
@Validated
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ProveedorDto> crear(@Valid @RequestBody ProveedorDto proveedorDto) {
        ProveedorDto creado = proveedorService.crearCliente(proveedorDto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDto> obtener(@PathVariable Long id) {
        ProveedorDto dto = proveedorService.obtenerCliente(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ProveedorDto>> listar() {
        List<ProveedorDto> lista = proveedorService.listarClientes();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProveedorDto proveedorDto) {
        ProveedorDto actualizado = proveedorService.actualizarCliente(id, proveedorDto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proveedorService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}