package com.tienda.Service;

import com.tienda.dto.VentaRequest;
import com.tienda.dto.VentaResponse;
import com.tienda.model.Cliente;
import com.tienda.model.DetalleVenta;
import com.tienda.model.Producto;
import com.tienda.model.Venta;
import com.tienda.repository.ClienteRepository;
import com.tienda.repository.ProductoRepository;
import com.tienda.repository.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock private VentaRepository ventaRepository;
    @Mock private ProductoRepository productoRepository;
    @Mock private ClienteRepository clienteRepository;
    @InjectMocks private VentaService ventaService;

    private static Producto producto(long id, String nombre, double precio, int stock) {
        Producto p = new Producto(nombre, "Abarrotes", BigDecimal.valueOf(precio), stock);
        p.setId(id);
        return p;
    }

    private static VentaRequest venta(Long clienteId, VentaRequest.Item... items) {
        return new VentaRequest(clienteId, List.of(items));
    }

    @Test
    void registrar_calculaTotalYDescuentaStock() {
        Producto arroz = producto(1, "Arroz", 4.50, 10);
        Producto leche = producto(2, "Leche", 4.20, 5);
        when(productoRepository.findParaActualizarById(1L)).thenReturn(Optional.of(arroz));
        when(productoRepository.findParaActualizarById(2L)).thenReturn(Optional.of(leche));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(inv -> inv.getArgument(0));

        VentaResponse r = ventaService.registrar(venta(null,
                new VentaRequest.Item(1L, 2), new VentaRequest.Item(2L, 3)));

        assertThat(r.total()).isEqualByComparingTo("21.60");
        assertThat(r.items()).hasSize(2);
        assertThat(arroz.getStock()).isEqualTo(8);
        assertThat(leche.getStock()).isEqualTo(2);
    }

    @Test
    void registrar_sumaCantidadesDeUnProductoRepetido() {
        Producto arroz = producto(1, "Arroz", 4.50, 10);
        when(productoRepository.findParaActualizarById(1L)).thenReturn(Optional.of(arroz));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(inv -> inv.getArgument(0));

        VentaResponse r = ventaService.registrar(venta(null,
                new VentaRequest.Item(1L, 2), new VentaRequest.Item(1L, 3)));

        assertThat(r.items()).singleElement().satisfies(i -> assertThat(i.cantidad()).isEqualTo(5));
        assertThat(arroz.getStock()).isEqualTo(5);
        verify(productoRepository, times(1)).findParaActualizarById(1L);
    }

    @Test
    void registrar_conCliente_asociaLaVenta() {
        Cliente maria = new Cliente("María", "Torres", "45879632", null, null);
        maria.setId(7L);
        when(clienteRepository.findById(7L)).thenReturn(Optional.of(maria));
        when(productoRepository.findParaActualizarById(1L)).thenReturn(Optional.of(producto(1, "Arroz", 4.50, 10)));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(inv -> inv.getArgument(0));

        VentaResponse r = ventaService.registrar(venta(7L, new VentaRequest.Item(1L, 1)));

        assertThat(r.cliente().id()).isEqualTo(7L);
        assertThat(r.cliente().nombre()).isEqualTo("María Torres");
    }

    @Test
    void registrar_clienteInexistente_404YNoTocaElStock() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ventaService.registrar(venta(99L, new VentaRequest.Item(1L, 1))))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(e -> assertThat(((ResponseStatusException) e).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
        verifyNoInteractions(productoRepository);
        verify(ventaRepository, never()).save(any());
    }

    @Test
    void registrar_productoInexistente_404() {
        when(productoRepository.findParaActualizarById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ventaService.registrar(venta(null, new VentaRequest.Item(99L, 1))))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Producto con id 99 no existe");
        verify(ventaRepository, never()).save(any());
    }

    @Test
    void registrar_stockInsuficiente_409YNoGuardaLaVenta() {
        Producto arroz = producto(1, "Arroz", 4.50, 10);
        Producto detergente = producto(2, "Detergente", 11.50, 3);
        when(productoRepository.findParaActualizarById(1L)).thenReturn(Optional.of(arroz));
        when(productoRepository.findParaActualizarById(2L)).thenReturn(Optional.of(detergente));

        assertThatThrownBy(() -> ventaService.registrar(venta(null,
                new VentaRequest.Item(1L, 1), new VentaRequest.Item(2L, 50))))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(e -> assertThat(((ResponseStatusException) e).getStatusCode()).isEqualTo(HttpStatus.CONFLICT))
                .hasMessageContaining("disponible 3");
        assertThat(detergente.getStock()).isEqualTo(3);
        verify(ventaRepository, never()).save(any());
    }

    @Test
    void buscar_inexistente_404() {
        when(ventaRepository.findConDetallesById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ventaService.buscar(5L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Venta con id 5 no existe");
    }

    @Test
    void anular_devuelveElStockYEliminaLaVenta() {
        Producto arroz = producto(1, "Arroz", 4.50, 6);
        Venta v = new Venta();
        v.agregarDetalle(new DetalleVenta(arroz, 4, new BigDecimal("4.50")));
        when(ventaRepository.findConDetallesById(3L)).thenReturn(Optional.of(v));
        when(productoRepository.findParaActualizarById(1L)).thenReturn(Optional.of(arroz));

        ventaService.anular(3L);

        assertThat(arroz.getStock()).isEqualTo(10);
        verify(ventaRepository).delete(v);
    }
}
