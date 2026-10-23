
package com.tienda.controller;

import com.tienda.Service.ProductoService;
import com.tienda.model.Producto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 1. LISTAR TODOS LOS PRODUCTOS
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(
                productoService.listarProductos()
        );
    }

    // 2. BUSCAR UN PRODUCTO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(
            @PathVariable("id") Long id) {

        return ResponseEntity.ok(
                productoService.obtenerProductoPorId(id)
        );
    }

    // 3. REGISTRAR UN NUEVO PRODUCTO
    @PostMapping
    public ResponseEntity<Producto> crearProducto(
            @RequestBody Producto producto) {

        Producto nuevoProducto =
                productoService.crearProducto(producto);

        URI ubicacion = URI.create(
                "/api/productos/" + nuevoProducto.getId()
        );

        return ResponseEntity
                .created(ubicacion)
                .body(nuevoProducto);
    }

    // 4. ACTUALIZAR UN PRODUCTO EXISTENTE
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable("id") Long id,
            @RequestBody Producto producto) {

        Producto productoActualizado =
                productoService.actualizarProducto(id, producto);

        return ResponseEntity.ok(productoActualizado);
    }

    // 5. ELIMINAR UN PRODUCTO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(
            @PathVariable("id") Long id) {

        productoService.eliminarProducto(id);

        return ResponseEntity.noContent().build();
    }
}
