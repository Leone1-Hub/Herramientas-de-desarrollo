package com.tienda.controller;

import com.tienda.Service.ProductoService;
import com.tienda.dto.Peticiones.ProductoRequest;
import com.tienda.model.Producto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping({"/api/productos", "/api/v1/productos"})
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody ProductoRequest producto) {
        Producto nuevoProducto = productoService.crearProducto(producto.aEntidad());
        return ResponseEntity.created(URI.create("/api/v1/productos/" + nuevoProducto.getId())).body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable("id") Long id, @RequestBody ProductoRequest producto) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, producto.aEntidad()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable("id") Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
