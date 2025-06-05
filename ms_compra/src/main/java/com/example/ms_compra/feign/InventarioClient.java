// src/main/java/com/example/ms_compra/feign/InventarioClient.java
package com.example.ms_compra.feign;

import com.example.ms_compra.dto.StockDto;
import com.example.ms_compra.dto.StockUpdateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Cliente Feign para ms-inventario-service.
 * Ahora usamos PUT /api/stock/{productoId}/reservar?cantidad=… 
 * y PUT /api/stock/{productoId}/reponer (body = StockUpdateDto).
 */
@FeignClient(name = "ms-inventario-service", url = "${feign.inventario.url}")
public interface InventarioClient {

    /**
     * Llama a PUT /api/stock/{productoId}/reservar?cantidad={cantidad}
     * Devuelve el StockDto actualizado (o 400 si no existe o no hay suficiente stock).
     */
    @PutMapping("/api/stock/{productoId}/reservar")
    StockDto reservarStock(
            @PathVariable("productoId") Long productoId,
            @RequestParam("cantidad") Integer cantidad
    );

    /**
     * Llama a PUT /api/stock/{productoId}/reponer
     * Body: {"cantidad": ...}
     * Devuelve el StockDto actualizado.
     */
    @PutMapping("/api/stock/{productoId}/reponer")
    StockDto reponeStock(
            @PathVariable("productoId") Long productoId,
            @RequestBody StockUpdateDto updateDto
    );
}
