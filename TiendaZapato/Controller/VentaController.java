package com.tiendazapatos.controller;

import com.tiendazapatos.model.Venta;
import com.tiendazapatos.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Venta> buscarVenta(@PathVariable Long id) {
        return ventaRepository.findById(id);
    }

    @PostMapping
    public Venta registrarVenta(@RequestBody Venta venta) {
        return ventaRepository.save(venta);
    }

    @PutMapping("/{id}")
    public Venta actualizarVenta(
            @PathVariable Long id,
            @RequestBody Venta venta) {

        venta.setId(id);
        return ventaRepository.save(venta);
    }

    @DeleteMapping("/{id}")
    public void eliminarVenta(@PathVariable Long id) {
        ventaRepository.deleteById(id);
    }
}