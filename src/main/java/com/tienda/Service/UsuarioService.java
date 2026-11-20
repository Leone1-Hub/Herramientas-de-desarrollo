package com.tienda.Service;

import com.tienda.model.Usuario;
import com.tienda.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final String FORMATO_EMAIL = "^[\\w.+-]++@[\\w-]++(?:\\.[\\w-]++)++$";

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario registrar(Usuario usuario) {
        usuario.setIdUsuario(null);
        validarDatos(usuario);

        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de usuario ya está registrado.");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo electrónico ya está registrado.");
        }

        usuario.setRol("CAJERO");
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setEstado(true);

        return usuarioRepository.save(usuario);
    }

    private void validarDatos(Usuario usuario) {
        if (esVacio(usuario.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario es obligatorio.");
        }
        usuario.setUsername(usuario.getUsername().trim());
        if (usuario.getUsername().length() < 3 || usuario.getUsername().length() > 50) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario debe tener entre 3 y 50 caracteres.");
        }
        if (esVacio(usuario.getPassword()) || usuario.getPassword().length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria y debe tener al menos 6 caracteres.");
        }
        if (esVacio(usuario.getNombre())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio.");
        }
        if (esVacio(usuario.getEmail()) || !usuario.getEmail().trim().matches(FORMATO_EMAIL)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo electrónico no tiene un formato válido.");
        }
        usuario.setEmail(usuario.getEmail().trim());
    }

    private static boolean esVacio(String valor) {
        return valor == null || valor.isBlank();
    }

    public Optional<Usuario> autenticar(String username, String rawPassword) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (Boolean.TRUE.equals(usuario.getEstado()) && passwordEncoder.matches(rawPassword, usuario.getPassword())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
}
