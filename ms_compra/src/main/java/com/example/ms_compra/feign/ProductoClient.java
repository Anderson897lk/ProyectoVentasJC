package com.example.ms_compra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-producto-service", url = "http://${feign.producto.base-url}")
public interface ProductoClient {
    @PutMapping("/api/productos/{id}/precio")
    void actualizarPrecio(@RequestParam Long id,
                          @RequestParam Double precioVenta);
}