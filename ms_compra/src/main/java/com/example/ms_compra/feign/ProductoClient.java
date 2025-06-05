package com.example.ms_compra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-producto-service", url = "${feign.producto.base-url}")
public interface ProductoClient {

    @PutMapping("/api/productos/{id}/precio")
    void actualizarPrecio(@PathVariable("id") Long productoId,
                          @RequestParam Double nuevoPrecio);
}