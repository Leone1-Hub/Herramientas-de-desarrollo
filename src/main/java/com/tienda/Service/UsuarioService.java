package com.tienda.Service;

import com.tienda.model.Usuario;
import com.tienda.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Usuario registrar(Usuario usuario) {
        // Validar unicidad
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está registrado.");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        // Normalizar y validar rol
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