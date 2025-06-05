package com.example.ms_compra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.ms_compra.dto.ProveedorDto;

@FeignClient(name = "ms-proveedor-service", url = "${feign.proveedor.base-url}")
public interface ProveedorClient {

    @GetMapping("/api/proveedores/{id}")
    ProveedorDto obtenerProveedor(@PathVariable("id") Long id);
}