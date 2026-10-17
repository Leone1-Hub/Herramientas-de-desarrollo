package com.tienda.Service;

import com.tienda.model.Usuario;
import com.tienda.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public java.util.List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}
