package com.example.ms_compra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.example.ms_compra.dto.ProveedorDto;

@FeignClient(name = "ms-proveedore-service", url = "${feign.proveedor.base-url}")
public interface ProveedorClient {
    @GetMapping("/api/proveedores/{id}")
    ProveedorDto obtenerProveedor(@PathVariable Long id);
}