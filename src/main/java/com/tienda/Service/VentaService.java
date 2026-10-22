package com.tienda.Service;

import com.tienda.dto.VentaRequest;
import com.tienda.dto.VentaResponse;
import com.tienda.model.DetalleVenta;
import com.tienda.model.Producto;
import com.tienda.model.Venta;
import com.tienda.repository.ClienteRepository;
import com.tienda.repository.ProductoRepository;
import com.tienda.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public Page<VentaResponse> listar(Pageable pageable) {
        return ventaRepository.findAll(pageable).map(VentaResponse::desde);
    }

    @Transactional(readOnly = true)
    public VentaResponse buscar(Long id) {
        return VentaResponse.desde(obtener(id));
    }

    @Transactional
    public VentaResponse registrar(VentaRequest request) {
        Venta venta = new Venta();
        if (request.clienteId() != null) {
            venta.setCliente(clienteRepository.findById(request.clienteId())
                    .orElseThrow(() -> noExiste("Cliente", request.clienteId())));
        }

        Map<Long, Integer> cantidades = new TreeMap<>();
        request.items().forEach(i -> cantidades.merge(i.productoId(), i.cantidad(), Integer::sum));

        cantidades.forEach((productoId, cantidad) -> {
            Producto producto = bloquear(productoId);
            if (producto.getStock() < cantidad) {
                throw new ResponseStatusException(CONFLICT, "Stock insuficiente para '%s': disponible %d, solicitado %d"
                        .formatted(producto.getNombre(), producto.getStock(), cantidad));
            }
            producto.setStock(producto.getStock() - cantidad);
            BigDecimal precio = BigDecimal.valueOf(producto.getPrecio()).setScale(2, RoundingMode.HALF_UP);
            venta.agregarDetalle(new DetalleVenta(producto, cantidad, precio));
        });

        return VentaResponse.desde(ventaRepository.save(venta));
    }

    @Transactional
    public void anular(Long id) {
        Venta venta = obtener(id);
        venta.getDetalles().forEach(d -> {
            Producto producto = bloquear(d.getProducto().getId());
            producto.setStock(producto.getStock() + d.getCantidad());
        });
        ventaRepository.delete(venta);
    }

    private Venta obtener(Long id) {
        return ventaRepository.findConDetallesById(id).orElseThrow(() -> noExiste("Venta", id));
    }

    private Producto bloquear(Long id) {
        return productoRepository.findParaActualizarById(id).orElseThrow(() -> noExiste("Producto", id));
    }

    private static ResponseStatusException noExiste(String recurso, Long id) {
        return new ResponseStatusException(NOT_FOUND, recurso + " con id " + id + " no existe");
    }
}
