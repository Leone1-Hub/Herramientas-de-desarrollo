package com.tienda.controller;

import com.tienda.Service.ClienteService;
import com.tienda.dto.Peticiones.ClienteRequest;
import com.tienda.model.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping({"/api/clientes", "/api/v1/clientes"})
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public List<Cliente> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con id " + id + " no existe"));
    }

    @PostMapping
    public ResponseEntity<Cliente> guardar(@RequestBody ClienteRequest cliente) {
        Cliente nuevo = service.crear(cliente.aEntidad());
        return ResponseEntity.created(URI.create("/api/v1/clientes/" + nuevo.getId())).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody ClienteRequest cliente) {
        return ResponseEntity.ok(service.actualizar(id, cliente.aEntidad()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
