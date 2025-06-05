package com.example.ms_compra.controller;

import com.example.ms_compra.dto.CompraDto;
import com.example.ms_compra.service.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
@Validated
public class CompraController {
    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<CompraDto> crear(@Valid @RequestBody CompraDto compraDto) {
        CompraDto creado = compraService.crearCompra(compraDto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDto> obtener(@PathVariable Long id) {
        CompraDto dto = compraService.obtenerCompra(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CompraDto>> listar() {
        List<CompraDto> lista = compraService.listarCompras();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CompraDto compraDto) {
        CompraDto actualizado = compraService.actualizarCompra(id, compraDto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        compraService.eliminarCompra(id);
        return ResponseEntity.noContent().build();
    }
}