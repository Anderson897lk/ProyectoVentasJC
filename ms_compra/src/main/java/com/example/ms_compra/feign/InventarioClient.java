package com.example.ms_compra.feign;

import com.example.ms_compra.dto.StockUpdateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-inventario-service", url = "http://${feign.inventario.base-url}")
public interface InventarioClient {

    /**
     * Llama a PUT /api/stock/{productoId}/reponer
     * con un JSON { "cantidad": … } en el body.
     */
    @PutMapping("/api/stock/{productoId}/reponer")
    void reponeStock(
            @PathVariable("productoId") Long productoId,
            @RequestBody StockUpdateDto updateDto
    );

    /**
     * Llama a PUT /api/stock/{productoId}/reservar?cantidad=…
     */
    @PutMapping("/api/stock/{productoId}/reservar")
    void reservaStock(
            @PathVariable("productoId") Long productoId,
            @RequestParam("cantidad") Integer cantidad
    );
}
