package com.tienda.dto;

import com.tienda.model.Cliente;
import com.tienda.model.Venta;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VentaResponse(
        Long id,
        LocalDateTime fecha,
        ClienteResumen cliente,
        BigDecimal total,
        List<Item> items
) {

    public record ClienteResumen(Long id, String nombre, String dni) {}

    public record Item(Long productoId, String producto, Integer cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {}

    public static VentaResponse desde(Venta venta) {
        Cliente c = venta.getCliente();
        return new VentaResponse(venta.getId(), venta.getFecha(),
                c == null ? null : new ClienteResumen(c.getId(), c.getNombre() + " " + c.getApellido(), c.getDni()),
                venta.getTotal(),
                venta.getDetalles().stream().map(d -> new Item(d.getProducto().getId(), d.getProducto().getNombre(),
                        d.getCantidad(), d.getPrecioUnitario(), d.getSubtotal())).toList());
    }
}
