package com.tienda.controller;

import com.tienda.model.Usuario;
import com.tienda.Service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.registrar(usuario);

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Usuario registrado exitosamente");
            response.put("idUsuario", nuevoUsuario.getIdUsuario());
            response.put("username", nuevoUsuario.getUsername());
            response.put("rol", nuevoUsuario.getRol());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String username = credenciales.get("username");
        String password = credenciales.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Faltan credenciales"));
        }

        Optional<Usuario> usuarioAuth = usuarioService.autenticar(username, password);

        if (usuarioAuth.isPresent()) {
            Usuario usuario = usuarioAuth.get();
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Inicio de sesión correcto");
            response.put("idUsuario", usuario.getIdUsuario());
            response.put("username", usuario.getUsername());
            response.put("rol", usuario.getRol());
            response.put("nombre", usuario.getNombre());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Usuario o contraseña incorrectos, o cuenta inactiva"));
        }
    }
}