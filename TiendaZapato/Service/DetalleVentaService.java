package com.tiendazapatos.service;

import com.tiendazapatos.model.DetalleVenta;
import com.tiendazapatos.repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public List<DetalleVenta> listarDetalles() {
        return detalleVentaRepository.findAll();
    }

    public Optional<DetalleVenta> buscarDetalle(Long id) {
        return detalleVentaRepository.findById(id);
    }

    public DetalleVenta guardarDetalle(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    public DetalleVenta actualizarDetalle(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    public void eliminarDetalle(Long id) {
        detalleVentaRepository.deleteById(id);
    }
}