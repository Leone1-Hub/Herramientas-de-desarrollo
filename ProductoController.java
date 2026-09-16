package com.tiendazapatos.controller;

import com.tiendazapatos.model.Producto;
import com.tiendazapatos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Listar todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Buscar producto por ID
    @GetMapping("/{id}")
    public Optional<Producto> buscarProducto(@PathVariable Long id) {
        return productoRepository.findById(id);
    }

    // Registrar producto
    @PostMapping
    public Producto registrarProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public Producto actualizarProducto(
            @PathVariable Long id,
            @RequestBody Producto producto) {

        producto.setId(id);
        return productoRepository.save(producto);
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
    }
}