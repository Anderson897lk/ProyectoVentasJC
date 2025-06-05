package com.example.ms_proveedores.controller;

import com.example.ms_proveedores.dto.ProveedorDto;
import com.example.ms_proveedores.dto.SunatResponseDto;
import com.example.ms_proveedores.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {
    private final ProveedorService service;

    @PostMapping
    public ResponseEntity<ProveedorDto> crear(@RequestBody ProveedorDto proveedorDto) {
        ProveedorDto creado = service.crearProveedor(proveedorDto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDto> obtenerPorId(@PathVariable Long id) {
        ProveedorDto dto = service.obtenerProveedorPorId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ProveedorDto>> listarTodos() {
        List<ProveedorDto> lista = service.listarProveedores();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDto> actualizar(@PathVariable Long id, @RequestBody ProveedorDto proveedorDto) {
        ProveedorDto actualizado = service.actualizarProveedor(id, proveedorDto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validar/{ruc}")
    public ResponseEntity<SunatResponseDto> validarRuc(@PathVariable String ruc) {
        SunatResponseDto respuesta = service.validarRucConSunat(ruc);
        return ResponseEntity.ok(respuesta);
    }
}