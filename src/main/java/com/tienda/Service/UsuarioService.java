package com.tienda.Service;

import com.tienda.model.Usuario;
import com.tienda.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private static final String FORMATO_EMAIL = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Usuario registrar(Usuario usuario) {
    
        validarDatos(usuario);

        // Validar usuario
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está registrado.");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        // validamos rol
        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()) {
            usuario.setRol("CAJERO"); // Rol por defecto
        } else {
            String rolUpper = usuario.getRol().trim().toUpperCase();
            if (!rolUpper.equals("ADMINISTRADOR") && !rolUpper.equals("CAJERO")) {
                throw new IllegalArgumentException("Rol inválido. Solo se permite ADMINISTRADOR o CAJERO.");
            }
            usuario.setRol(rolUpper);
        }

        // Cifrado de contraseña con BCrypt (listo para Spring Security)
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setEstado(true);

        return usuarioRepository.save(usuario);
    }

    private void validarDatos(Usuario usuario) {
        if (esVacio(usuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio.");
        }
        usuario.setUsername(usuario.getUsername().trim());
        if (usuario.getUsername().length() < 3 || usuario.getUsername().length() > 50) {
            throw new IllegalArgumentException("El nombre de usuario debe tener entre 3 y 50 caracteres.");
        }
        if (esVacio(usuario.getPassword()) || usuario.getPassword().length() < 6) {
            throw new IllegalArgumentException("La contraseña es obligatoria y debe tener al menos 6 caracteres.");
        }
        if (esVacio(usuario.getNombre())) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (esVacio(usuario.getEmail()) || !usuario.getEmail().trim().matches(FORMATO_EMAIL)) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido.");
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
            // Comprueba si está activo y valida la contraseña encriptada
            if (Boolean.TRUE.equals(usuario.getEstado()) && passwordEncoder.matches(rawPassword, usuario.getPassword())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
}