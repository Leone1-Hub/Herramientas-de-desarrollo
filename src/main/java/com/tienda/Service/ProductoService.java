package com.tienda.Service;

import com.tienda.model.Producto;
import com.tienda.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el producto con ID: " + id
                ));
    }

    @Transactional
    public Producto crearProducto(Producto producto) {
        validarProducto(producto);

        // El ID se genera automáticamente al registrar.
        producto.setId(null);

        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizarProducto(Long id, Producto datosActualizados) {
        validarProducto(datosActualizados);

        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el producto con ID: " + id
                ));

        productoExistente.setNombre(datosActualizados.getNombre());
        productoExistente.setCategoria(datosActualizados.getCategoria());
        productoExistente.setPrecio(datosActualizados.getPrecio());
        productoExistente.setStock(datosActualizados.getStock());

        return productoRepository.save(productoExistente);
    }

    @Transactional
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No se encontró el producto con ID: " + id
            );
        }

        productoRepository.deleteById(id);
    }

    private void validarProducto(Producto producto) {
        if (producto == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Los datos del producto son obligatorios"
            );
        }

        if (producto.getNombre() == null
                || producto.getNombre().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre del producto es obligatorio"
            );
        }

        if (producto.getPrecio() == null
                || producto.getPrecio() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El precio debe ser mayor o igual a cero"
            );
        }

        if (producto.getStock() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El stock no puede ser negativo"
            );
        }
    }
}
