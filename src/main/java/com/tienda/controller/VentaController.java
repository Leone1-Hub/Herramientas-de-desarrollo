package com.tienda.controller;

import com.tienda.Service.VentaService;
import com.tienda.dto.VentaRequest;
import com.tienda.dto.VentaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public PagedModel<VentaResponse> listar(@PageableDefault(sort = "fecha", direction = Sort.Direction.DESC) Pageable pageable) {
        return new PagedModel<>(ventaService.listar(pageable));
    }

    @GetMapping("/{id}")
    public VentaResponse buscar(@PathVariable Long id) {
        return ventaService.buscar(id);
    }

    @PostMapping
    public ResponseEntity<VentaResponse> registrar(@Valid @RequestBody VentaRequest request) {
        VentaResponse venta = ventaService.registrar(request);
        return ResponseEntity.created(URI.create("/api/ventas/" + venta.id())).body(venta);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void anular(@PathVariable Long id) {
        ventaService.anular(id);
    }
}