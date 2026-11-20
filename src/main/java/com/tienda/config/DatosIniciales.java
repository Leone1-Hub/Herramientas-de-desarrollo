package com.tienda.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tienda.model.Cliente;
import com.tienda.model.Producto;
import com.tienda.model.Usuario;
import com.tienda.repository.ClienteRepository;
import com.tienda.repository.ProductoRepository;
import com.tienda.repository.UsuarioRepository;

@Configuration
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarDatos(
            ProductoRepository productos,
            ClienteRepository clientes,
            UsuarioRepository usuarios,
            PasswordEncoder passwordEncoder,
            @Value("${ADMIN_PASSWORD:Admin123}") String adminPassword) {
        return args -> {
            if (productos.count() == 0) {
                productos.saveAll(List.of(
                        new Producto("Arroz Costeño 1 kg", "Abarrotes", new BigDecimal("4.50"), 100),
                        new Producto("Azúcar rubia 1 kg", "Abarrotes", new BigDecimal("3.80"), 80),
                        new Producto("Aceite Primor 1 L", "Abarrotes", new BigDecimal("9.90"), 50),
                        new Producto("Leche Gloria 400 g", "Lácteos", new BigDecimal("4.20"), 120),
                        new Producto("Fideos Don Vittorio 500 g", "Abarrotes", new BigDecimal("3.20"), 90),
                        new Producto("Atún Florida 170 g", "Conservas", new BigDecimal("6.50"), 60),
                        new Producto("Inca Kola 1.5 L", "Bebidas", new BigDecimal("7.00"), 40),
                        new Producto("Detergente Bolívar 900 g", "Limpieza", new BigDecimal("11.50"), 3)
                ));
            }
            if (clientes.count() == 0) {
                clientes.save(new Cliente("María", "Torres", "45879632", "987654321", "maria.torres@correo.pe"));
            }
            if (!usuarios.existsByRol("ADMINISTRADOR") && !usuarios.existsByUsername("admin")) {
                Usuario admin = new Usuario(
                        "admin",
                        passwordEncoder.encode(adminPassword),
                        "Administrador",
                        "999999999",
                        "admin@tienda.com",
                        "ADMINISTRADOR"
                );
                admin.setEstado(true);
                usuarios.save(admin);
            }
        };
    }
}