package com.tienda.integracion;

import com.tienda.model.Producto;
import com.tienda.repository.ProductoRepository;
import com.tienda.repository.VentaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VentaIntegracionTest {

    private static final RequestPostProcessor CAJERO = jwt().authorities(new SimpleGrantedAuthority("ROLE_CAJERO"));

    @Autowired private MockMvc mvc;
    @Autowired private ProductoRepository productos;
    @Autowired private VentaRepository ventas;

    private Producto arroz;
    private Producto detergente;

    @BeforeEach
    void crearProductos() {
        arroz = productos.save(new Producto("Arroz test", "Abarrotes", 4.50, 10));
        detergente = productos.save(new Producto("Detergente test", "Limpieza", 11.50, 3));
    }

    @AfterEach
    void limpiar() {
        ventas.deleteAll();
        productos.deleteAll(List.of(arroz, detergente));
    }

    private int stock(Producto p) {
        return productos.findById(p.getId()).orElseThrow().getStock();
    }

    @Test
    void registrarVenta_201_calculaTotalYDescuentaStock() throws Exception {
        mvc.perform(post("/api/ventas").with(CAJERO).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"items\":[{\"productoId\":" + arroz.getId() + ",\"cantidad\":2}]}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.total").value(9.00));

        assertThat(stock(arroz)).isEqualTo(8);
    }

    @Test
    void rollback_stockInsuficiente_noDescuentaNadaNiGuardaLaVenta() throws Exception {
        long ventasAntes = ventas.count();

        mvc.perform(post("/api/ventas").with(CAJERO).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"items\":[{\"productoId\":" + arroz.getId() + ",\"cantidad\":2},"
                                + "{\"productoId\":" + detergente.getId() + ",\"cantidad\":50}]}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value(containsString("disponible 3")));

        assertThat(stock(arroz)).as("el descuento del arroz se revirtió").isEqualTo(10);
        assertThat(stock(detergente)).isEqualTo(3);
        assertThat(ventas.count()).as("la venta fallida no quedó guardada").isEqualTo(ventasAntes);
    }

    @Test
    void anularVenta_204_devuelveElStock() throws Exception {
        String location = mvc.perform(post("/api/ventas").with(CAJERO).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"items\":[{\"productoId\":" + arroz.getId() + ",\"cantidad\":4}]}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");
        assertThat(stock(arroz)).isEqualTo(6);

        mvc.perform(delete(location).with(CAJERO)).andExpect(status().isNoContent());

        assertThat(stock(arroz)).isEqualTo(10);
        mvc.perform(get(location).with(CAJERO)).andExpect(status().isNotFound());
    }

    @Test
    void validaciones_400_y_recursosInexistentes_404() throws Exception {
        mvc.perform(post("/api/ventas").with(CAJERO).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"items\":[{\"productoId\":" + arroz.getId() + ",\"cantidad\":-5}]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores[0]").value(containsString("cantidad")));
        mvc.perform(post("/api/ventas").with(CAJERO).contentType(MediaType.APPLICATION_JSON).content("{\"items\":[]}"))
                .andExpect(status().isBadRequest());
        mvc.perform(post("/api/ventas").with(CAJERO).contentType(MediaType.APPLICATION_JSON).content("{\"items\":"))
                .andExpect(status().isBadRequest());
        mvc.perform(post("/api/ventas").with(CAJERO).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clienteId\":999999,\"items\":[{\"productoId\":" + arroz.getId() + ",\"cantidad\":1}]}"))
                .andExpect(status().isNotFound());
        mvc.perform(get("/api/ventas/999999").with(CAJERO)).andExpect(status().isNotFound());
        mvc.perform(get("/api/ventas/abc").with(CAJERO)).andExpect(status().isBadRequest());
    }

    @Test
    void listar_paginadoConTopeDe100() throws Exception {
        mvc.perform(get("/api/ventas?size=5000").with(CAJERO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.size").value(100));
    }
}
