package com.tienda.Service;

import com.tienda.model.Producto;
import com.tienda.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock private ProductoRepository repository;
    @InjectMocks private ProductoService service;

    private static HttpStatus estado(Throwable e) {
        return (HttpStatus) ((ResponseStatusException) e).getStatusCode();
    }

    @Test
    void listar_devuelveLosProductos() {
        when(repository.findAll()).thenReturn(List.of(new Producto("Arroz", "Abarrotes", 4.5, 10)));

        assertThat(service.listarProductos()).hasSize(1);
    }

    @Test
    void obtener_inexistente_404() {
        when(repository.findById(9L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.obtenerProductoPorId(9L))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.NOT_FOUND));
    }

    @Test
    void crear_ignoraElIdEnviadoYGuarda() {
        Producto p = new Producto("Pan", "Panadería", 8.9, 25);
        p.setId(1L);
        when(repository.save(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        assertThat(service.crearProducto(p).getId()).isNull();
    }

    @Test
    void crear_sinNombre_400() {
        assertThatThrownBy(() -> service.crearProducto(new Producto(" ", null, 1.0, 1)))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.BAD_REQUEST))
                .hasMessageContaining("nombre");
    }

    @Test
    void crear_precioNegativo_400() {
        assertThatThrownBy(() -> service.crearProducto(new Producto("Pan", null, -1.0, 1)))
                .hasMessageContaining("precio");
    }

    @Test
    void crear_sinPrecio_400() {
        assertThatThrownBy(() -> service.crearProducto(new Producto("Pan", null, null, 1)))
                .hasMessageContaining("precio");
    }

    @Test
    void crear_stockNegativo_400() {
        assertThatThrownBy(() -> service.crearProducto(new Producto("Pan", null, 1.0, -5)))
                .hasMessageContaining("stock");
        verify(repository, never()).save(any());
    }

    @Test
    void crear_nulo_400() {
        assertThatThrownBy(() -> service.crearProducto(null))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    void actualizar_modificaLosDatos() {
        Producto actual = new Producto("Pan", "Panadería", 8.9, 25);
        when(repository.findById(1L)).thenReturn(Optional.of(actual));
        when(repository.save(actual)).thenReturn(actual);

        Producto r = service.actualizarProducto(1L, new Producto("Pan integral", "Panadería", 9.5, 30));

        assertThat(r.getNombre()).isEqualTo("Pan integral");
        assertThat(r.getPrecio()).isEqualTo(9.5);
        assertThat(r.getStock()).isEqualTo(30);
    }

    @Test
    void actualizar_inexistente_404() {
        when(repository.findById(9L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.actualizarProducto(9L, new Producto("Pan", null, 1.0, 1)))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.NOT_FOUND));
    }

    @Test
    void eliminar_inexistente_404YExistenteBorra() {
        when(repository.existsById(9L)).thenReturn(false);
        when(repository.existsById(1L)).thenReturn(true);

        assertThatThrownBy(() -> service.eliminarProducto(9L))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.NOT_FOUND));
        service.eliminarProducto(1L);
        verify(repository).deleteById(1L);
    }
}
