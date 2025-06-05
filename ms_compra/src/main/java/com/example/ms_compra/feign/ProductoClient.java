package com.example.ms_compra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-producto-service", url = "${feign.producto.base-url}")
public interface ProductoClient {
    @PutMapping("/api/productos/{id}/actualizarPrecio")
    void actualizarPrecio(@PathVariable Long id, @RequestParam Double nuevoPrecio);

    @PutMapping("/api/productos/{id}/actualizarStock")
    void actualizarStock(@PathVariable Long id, @RequestParam Integer cantidad);
}