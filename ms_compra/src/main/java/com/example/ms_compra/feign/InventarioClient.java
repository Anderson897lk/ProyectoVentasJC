package com.example.ms_compra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-inventario-service", url = "${feign.inventario.base-url}")
public interface InventarioClient {
    @PostMapping("/api/inventarios/registrarMovimiento")
    void registrarMovimiento(@RequestParam Long productoId,
                             @RequestParam Integer cantidad,
                             @RequestParam String tipoMovimiento);
}