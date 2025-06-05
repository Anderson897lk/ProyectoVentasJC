package com.example.ms_compra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-inventario-service", url = "http://${feign.inventario.base-url}")
public interface InventarioClient {
    @PostMapping("/api/stock/{productoId}/reponer")
    void reponeStock(@RequestParam Long productoId,
                     @RequestParam Integer cantidad);

    @PostMapping("/api/stock/{productoId}/reservar")
    void reservaStock(@RequestParam Long productoId,
                      @RequestParam Integer cantidad);
}