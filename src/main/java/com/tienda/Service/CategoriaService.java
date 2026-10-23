package com.tienda.Service;

import com.tienda.model.Categoria;
import com.tienda.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    @Transactional
    public Categoria guardarCategoria(Categoria categoria) {
        validar(categoria);
        categoria.setId(null);
        if (categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre())) {
            throw new ResponseStatusException(CONFLICT, "Ya existe una categoría con ese nombre.");
        }
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria actualizarCategoria(Long id, Categoria categoria) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(id);

        if (categoriaExistente.isPresent()) {
            validar(categoria);
            if (categoriaRepository.existsByNombreIgnoreCaseAndIdNot(categoria.getNombre(), id)) {
                throw new ResponseStatusException(CONFLICT, "Ya existe otra categoría con ese nombre.");
            }
            Categoria actual = categoriaExistente.get();
            actual.setNombre(categoria.getNombre());
            actual.setDescripcion(categoria.getDescripcion());
            return categoriaRepository.save(actual);
        }

        return null;
    }

    @Transactional
    public boolean eliminarCategoria(Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return true;
        }

        return false;
    }

    private void validar(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isBlank()) {
            throw new ResponseStatusException(BAD_REQUEST, "El nombre de la categoría es obligatorio.");
        }
        categoria.setNombre(categoria.getNombre().trim());
        if (categoria.getNombre().length() > 50) {
            throw new ResponseStatusException(BAD_REQUEST, "El nombre de la categoría admite máximo 50 caracteres.");
        }
        if (categoria.getDescripcion() != null && categoria.getDescripcion().length() > 150) {
            throw new ResponseStatusException(BAD_REQUEST, "La descripción admite máximo 150 caracteres.");
        }
    }
}
