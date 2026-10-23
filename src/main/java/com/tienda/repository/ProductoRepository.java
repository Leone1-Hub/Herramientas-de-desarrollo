package com.tienda.repository;

import com.tienda.model.Producto;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Producto> findParaActualizarById(Long id);
}
