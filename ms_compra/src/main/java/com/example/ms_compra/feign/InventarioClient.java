package com.example.ms_compra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-inventario-service", url = "${feign.inventario.base-url}")
public interface InventarioClient {

    @PutMapping("/api/stock/{productoId}/reservar")
    void reservarStock(@PathVariable("productoId") Long productoId,
                       @RequestParam("cantidad") Integer cantidad);

    @PutMapping("/api/stock/{productoId}/reponer")
    void reponerStock(@PathVariable("productoId") Long productoId,
                      @RequestParam("cantidad") Integer cantidad);
}
