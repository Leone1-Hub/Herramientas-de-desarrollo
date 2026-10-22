package com.tienda.repository;

import com.tienda.model.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Override
    @EntityGraph(attributePaths = "cliente")
    Page<Venta> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"cliente", "detalles", "detalles.producto"})
    Optional<Venta> findConDetallesById(Long id);
}
