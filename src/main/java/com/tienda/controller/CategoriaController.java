package com.tienda.controller;

import com.tienda.Service.CategoriaService;
import com.tienda.dto.Peticiones.CategoriaRequest;
import com.tienda.model.Categoria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping({"/api/categorias", "/api/v1/categorias"})
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaService.listarCategorias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody CategoriaRequest categoria) {
        Categoria nueva = categoriaService.guardarCategoria(categoria.aEntidad());
        return ResponseEntity.created(URI.create("/api/v1/categorias/" + nueva.getId())).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaRequest categoria) {
        Categoria actualizada = categoriaService.actualizarCategoria(id, categoria.aEntidad());
        return actualizada == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        return categoriaService.eliminarCategoria(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
