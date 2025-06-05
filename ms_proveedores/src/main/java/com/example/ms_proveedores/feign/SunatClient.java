package com.example.ms_proveedores.feign;

import com.example.ms_proveedores.dto.SunatResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sunat-client", url = "${feign.sunat.base-url}")
public interface SunatClient {
    @GetMapping("/ruc/{ruc}")
    SunatResponseDto validarRuc(@PathVariable("ruc") String ruc);
}