package com.tienda.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tienda.model.Cliente;
import com.tienda.model.Producto;
import com.tienda.repository.ClienteRepository;
import com.tienda.repository.ProductoRepository;

@Configuration
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarDatos(ProductoRepository productos, ClienteRepository clientes) {
        return args -> {
            if (productos.count() == 0) {
                productos.saveAll(List.of(
                        new Producto("Arroz Costeño 1 kg", "Abarrotes", 4.50, 100),
                        new Producto("Azúcar rubia 1 kg", "Abarrotes", 3.80, 80),
                        new Producto("Aceite Primor 1 L", "Abarrotes", 9.90, 50),
                        new Producto("Leche Gloria 400 g", "Lácteos", 4.20, 120),
                        new Producto("Fideos Don Vittorio 500 g", "Abarrotes", 3.20, 90),
                        new Producto("Atún Florida 170 g", "Conservas", 6.50, 60),
                        new Producto("Inca Kola 1.5 L", "Bebidas", 7.00, 40),
                        new Producto("Detergente Bolívar 900 g", "Limpieza", 11.50, 3)
                ));
            }
            if (clientes.count() == 0) {
                clientes.save(new Cliente("María", "Torres", "45879632", "987654321", "maria.torres@correo.pe"));
            }
        };
    }
}
