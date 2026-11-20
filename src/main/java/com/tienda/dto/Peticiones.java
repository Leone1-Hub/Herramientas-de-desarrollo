package com.tienda.dto;

import com.tienda.model.Categoria;
import com.tienda.model.Cliente;
import com.tienda.model.Producto;
import com.tienda.model.Usuario;

import java.math.BigDecimal;

public interface Peticiones {

    record ProductoRequest(String nombre, String categoria, BigDecimal precio, int stock) {
        public Producto aEntidad() { return new Producto(nombre, categoria, precio, stock); }
    }

    record ClienteRequest(String nombre, String apellido, String dni, String telefono, String correo) {
        public Cliente aEntidad() { return new Cliente(nombre, apellido, dni, telefono, correo); }
    }

    record CategoriaRequest(String nombre, String descripcion) {
        public Categoria aEntidad() { return new Categoria(nombre, descripcion); }
    }

    record RegistroRequest(String username, String password, String nombre, String telefono, String email) {
        public Usuario aEntidad() { return new Usuario(username, password, nombre, telefono, email, null); }
    }
}
