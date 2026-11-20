package com.tienda.Service;

import com.tienda.model.Usuario;
import com.tienda.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock private UsuarioRepository repository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private UsuarioService service;

    private static Usuario usuario(String username, String password, String email) {
        return new Usuario(username, password, "Ana", null, email, null);
    }

    private static HttpStatus estado(Throwable e) {
        return (HttpStatus) ((ResponseStatusException) e).getStatusCode();
    }

    @Test
    void registrar_ignoraIdYRolEnviadosYCifraLaClave() {
        Usuario u = usuario("cajero1", "Clave123", "c@t.pe");
        u.setIdUsuario(1L);
        u.setRol("ADMINISTRADOR");
        when(passwordEncoder.encode("Clave123")).thenReturn("$hash");
        when(repository.save(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        Usuario r = service.registrar(u);

        assertThat(r.getIdUsuario()).as("no puede sobrescribir otra cuenta").isNull();
        assertThat(r.getRol()).isEqualTo("CAJERO");
        assertThat(r.getPassword()).isEqualTo("$hash");
        assertThat(r.getEstado()).isTrue();
    }

    @Test
    void registrar_usernameRepetido_409() {
        when(repository.existsByUsername("admin")).thenReturn(true);

        assertThatThrownBy(() -> service.registrar(usuario("admin", "Clave123", "a@t.pe")))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.CONFLICT));
        verify(repository, never()).save(any());
    }

    @Test
    void registrar_emailRepetido_409() {
        when(repository.existsByUsername("nuevo")).thenReturn(false);
        when(repository.existsByEmail("a@t.pe")).thenReturn(true);

        assertThatThrownBy(() -> service.registrar(usuario("nuevo", "Clave123", "a@t.pe")))
                .satisfies(e -> assertThat(estado(e)).isEqualTo(HttpStatus.CONFLICT));
    }

    @Test
    void registrar_validaLosDatos() {
        assertThatThrownBy(() -> service.registrar(usuario(null, "Clave123", "a@t.pe"))).hasMessageContaining("usuario es obligatorio");
        assertThatThrownBy(() -> service.registrar(usuario("ab", "Clave123", "a@t.pe"))).hasMessageContaining("entre 3 y 50");
        assertThatThrownBy(() -> service.registrar(usuario("pepe", "123", "a@t.pe"))).hasMessageContaining("6 caracteres");
        assertThatThrownBy(() -> service.registrar(usuario("pepe", "Clave123", "hola"))).hasMessageContaining("correo");
        assertThatThrownBy(() -> service.registrar(new Usuario("pepe", "Clave123", " ", null, "a@t.pe", null))).hasMessageContaining("nombre");
        verifyNoInteractions(repository);
    }

    @Test
    void autenticar_credencialesCorrectas() {
        Usuario u = usuario("admin", "$hash", "a@t.pe");
        when(repository.findByUsername("admin")).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("Admin123", "$hash")).thenReturn(true);

        assertThat(service.autenticar("admin", "Admin123")).contains(u);
    }

    @Test
    void autenticar_claveIncorrecta() {
        when(repository.findByUsername("admin")).thenReturn(Optional.of(usuario("admin", "$hash", "a@t.pe")));
        when(passwordEncoder.matches("mala", "$hash")).thenReturn(false);

        assertThat(service.autenticar("admin", "mala")).isEmpty();
    }

    @Test
    void autenticar_cuentaInactiva() {
        Usuario u = usuario("admin", "$hash", "a@t.pe");
        u.setEstado(false);
        when(repository.findByUsername("admin")).thenReturn(Optional.of(u));

        assertThat(service.autenticar("admin", "Admin123")).isEmpty();
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void autenticar_usuarioInexistente() {
        when(repository.findByUsername("nadie")).thenReturn(Optional.empty());

        assertThat(service.autenticar("nadie", "x")).isEmpty();
    }
}
