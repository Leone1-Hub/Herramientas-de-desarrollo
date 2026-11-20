package com.tienda.controller;

import com.tienda.Service.JwtService;
import com.tienda.dto.Peticiones.RegistroRequest;
import com.tienda.Service.UsuarioService;
import com.tienda.dto.LoginResponse;
import com.tienda.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping({"/api/auth", "/api/v1/auth"})
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody RegistroRequest usuario) {
        Usuario nuevoUsuario = usuarioService.registrar(usuario.aEntidad());

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Usuario registrado exitosamente");
        response.put("idUsuario", nuevoUsuario.getIdUsuario());
        response.put("username", nuevoUsuario.getUsername());
        response.put("rol", nuevoUsuario.getRol());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Map<String, String> credenciales) {
        String username = credenciales.get("username");
        String password = credenciales.get("password");

        if (username == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Faltan credenciales");
        }

        Optional<Usuario> usuarioAuth = usuarioService.autenticar(username, password);

        if (usuarioAuth.isPresent()) {
            Usuario usuario = usuarioAuth.get();
            String token = jwtService.generarToken(usuario);
            return ResponseEntity.ok(LoginResponse.of(token, usuario.getUsername(), usuario.getRol()));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos, o cuenta inactiva");
        }
    }
}
