package com.tienda.Service;

import com.tienda.model.Categoria;
import com.tienda.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock private CategoriaRepository repository;
    @InjectMocks private CategoriaService service;

    private static HttpStatus estado(Throwable e) {
        return (HttpStatus) ((ResponseStatusException) e).getStatusCode();
    }

    @Test
    void guardar_recortaEspaciosYGuarda() {
        when(repository.existsByNombreIgnoreCase("Abarrotes")).thenReturn(false);
        when(repository.save(any(Categoria.class))).thenAnswer(inv -> inv.getArgument(0));

        Categoria c = service.guardarCategoria(new Categoria("  Abarrotes  ", "Secos"));

        assertThat(c.getNombre()).isEqualTo("Abarrotes");
        assertThat(c.getId()).isNull();
    }

    @Test
    void guardar_nombreDuplicado_409() {
        when(repository.existsByNombreIgnoreCase("Lácteos")).thenReturn(true);

        assertThatThrownBy(() -> service.guardarCategoria(new Categoria("Lácteos", null)))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.CONFLICT));
        verify(repository, never()).save(any());
    }

    @Test
    void guardar_sinNombre_400() {
        assertThatThrownBy(() -> service.guardarCategoria(new Categoria(null, "x")))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    void guardar_nombreDeMasDe50Caracteres_400() {
        assertThatThrownBy(() -> service.guardarCategoria(new Categoria("x".repeat(51), null)))
                .hasMessageContaining("50 caracteres");
    }

    @Test
    void guardar_descripcionDeMasDe150Caracteres_400() {
        assertThatThrownBy(() -> service.guardarCategoria(new Categoria("Bebidas", "x".repeat(151))))
                .hasMessageContaining("150 caracteres");
    }

    @Test
    void actualizar_inexistente_devuelveNull() {
        when(repository.findById(9L)).thenReturn(Optional.empty());

        assertThat(service.actualizarCategoria(9L, new Categoria("Bebidas", null))).isNull();
    }

    @Test
    void actualizar_nombreDeOtraCategoria_409() {
        when(repository.findById(1L)).thenReturn(Optional.of(new Categoria("Bebidas", null)));
        when(repository.existsByNombreIgnoreCaseAndIdNot("Lácteos", 1L)).thenReturn(true);

        assertThatThrownBy(() -> service.actualizarCategoria(1L, new Categoria("Lácteos", null)))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.CONFLICT));
    }

    @Test
    void actualizar_modificaNombreYDescripcion() {
        Categoria actual = new Categoria("Bebidas", "Gaseosas");
        when(repository.findById(1L)).thenReturn(Optional.of(actual));
        when(repository.existsByNombreIgnoreCaseAndIdNot("Bebidas", 1L)).thenReturn(false);
        when(repository.save(actual)).thenReturn(actual);

        Categoria r = service.actualizarCategoria(1L, new Categoria("Bebidas", "Gaseosas y jugos"));

        assertThat(r.getDescripcion()).isEqualTo("Gaseosas y jugos");
    }

    @Test
    void eliminar_existenteYNoExistente() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.existsById(9L)).thenReturn(false);

        assertThat(service.eliminarCategoria(1L)).isTrue();
        assertThat(service.eliminarCategoria(9L)).isFalse();
        verify(repository).deleteById(1L);
        verify(repository, never()).deleteById(9L);
    }
}
