public class ClienteRepository {
    package com.tienda.repository;

import com.tienda.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository
        extends JpaRepository<Cliente, Long> {
}
}
