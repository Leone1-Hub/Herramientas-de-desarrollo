package com.tienda.Service;

import com.tienda.model.Cliente;
import com.tienda.repository.ClienteRepository;
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
class ClienteServiceTest {

    @Mock private ClienteRepository repository;
    @InjectMocks private ClienteService service;

    private static HttpStatus estado(Throwable e) {
        return (HttpStatus) ((ResponseStatusException) e).getStatusCode();
    }

    @Test
    void crear_ignoraElIdEnviadoYGuarda() {
        Cliente c = new Cliente("Luis", "Ramos", "70123456", null, null);
        c.setId(1L);
        when(repository.existsByDni("70123456")).thenReturn(false);
        when(repository.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

        Cliente guardado = service.crear(c);

        assertThat(guardado.getId()).isNull();
        verify(repository).save(c);
    }

    @Test
    void crear_dniDuplicado_409() {
        when(repository.existsByDni("45879632")).thenReturn(true);

        assertThatThrownBy(() -> service.crear(new Cliente("Otro", null, "45879632", null, null)))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.CONFLICT));
        verify(repository, never()).save(any());
    }

    @Test
    void crear_dniConMenosDe8Digitos_400() {
        assertThatThrownBy(() -> service.crear(new Cliente("Otro", null, "1234567", null, null)))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.BAD_REQUEST))
                .hasMessageContaining("8 dígitos");
    }

    @Test
    void crear_sinNombre_400() {
        assertThatThrownBy(() -> service.crear(new Cliente(" ", null, null, null, null)))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    void actualizar_inexistente_404() {
        when(repository.findById(9L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.actualizar(9L, new Cliente("Luis", null, null, null, null)))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.NOT_FOUND));
    }

    @Test
    void actualizar_dniDeOtroCliente_409() {
        when(repository.findById(2L)).thenReturn(Optional.of(new Cliente("Luis", null, "70123456", null, null)));
        when(repository.existsByDniAndIdNot("45879632", 2L)).thenReturn(true);

        assertThatThrownBy(() -> service.actualizar(2L, new Cliente("Luis", null, "45879632", null, null)))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.CONFLICT));
    }

    @Test
    void actualizar_modificaLosDatos() {
        Cliente actual = new Cliente("Luis", "Ramos", "70123456", null, null);
        when(repository.findById(2L)).thenReturn(Optional.of(actual));
        when(repository.existsByDniAndIdNot("70123456", 2L)).thenReturn(false);
        when(repository.save(actual)).thenReturn(actual);

        Cliente r = service.actualizar(2L, new Cliente("Luis", "Ramos Díaz", "70123456", "999", "l@c.pe"));

        assertThat(r.getApellido()).isEqualTo("Ramos Díaz");
        assertThat(r.getCorreo()).isEqualTo("l@c.pe");
    }

    @Test
    void eliminar_inexistente_404() {
        when(repository.existsById(9L)).thenReturn(false);

        assertThatThrownBy(() -> service.eliminar(9L))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.NOT_FOUND));
        verify(repository, never()).deleteById(any());
    }
}
