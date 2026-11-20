
package com.tienda.controller;

import com.tienda.model.Categoria;
import com.tienda.Service.CategoriaService;
import com.tienda.dto.Peticiones.CategoriaRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping({"/api/categorias", "/api/v1/categorias"})
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    // Constructor para inyección de dependencias
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // LISTAR CATEGORÍAS
    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaService.listarCategorias();
    }

    // BUSCAR CATEGORÍA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(
            @PathVariable Long id) {

        return categoriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "La categoría con ID " + id + " no existe."
                ));
    }

    // CREAR CATEGORÍA
    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(
            @RequestBody Categoria categoria) {

        Categoria nuevaCategoria =
                categoriaService.guardarCategoria(categoria);

        return ResponseEntity
                .created(URI.create(
                        "/api/v1/categorias/" + nuevaCategoria.getId()
                ))
                .body(nuevaCategoria);
    }

    // ACTUALIZAR CATEGORÍA
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(
            @PathVariable Long id,
            @RequestBody CategoriaRequest categoria) {

        Categoria categoriaActualizada =
                categoriaService.actualizarCategoria(
                        id,
                        categoria.aEntidad()
                );

        if (categoriaActualizada == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La categoría con ID " + id + " no existe."
            );
        }

        return ResponseEntity.ok(categoriaActualizada);
    }

    // ELIMINAR CATEGORÍA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(
            @PathVariable Long id) {

        boolean eliminado =
                categoriaService.eliminarCategoria(id);

        if (!eliminado) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La categoría con ID " + id + " no existe."
            );
        }

        return ResponseEntity.noContent().build();
    }
}
