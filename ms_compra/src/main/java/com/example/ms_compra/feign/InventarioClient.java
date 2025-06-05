package com.example.ms_compra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.ms_compra.dto.StockUpdateDto;

@FeignClient(name = "ms-inventario-service", url = "${feign.inventario.base-url}")
public interface InventarioClient {

    // Reserva stock (SALIDA)
    @PutMapping("/api/stock/{productoId}/reservar")
    void reservarStock(@PathVariable("productoId") Long productoId,
                       @RequestBody StockUpdateDto updateDto);

    // Reponer stock (ENTRADA)
    @PutMapping("/api/stock/{productoId}/reponer")
    void reponerStock(@PathVariable("productoId") Long productoId,
                      @RequestBody StockUpdateDto updateDto);
}