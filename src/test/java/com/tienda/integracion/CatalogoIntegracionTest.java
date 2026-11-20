package com.tienda.integracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CatalogoIntegracionTest {

    private static final RequestPostProcessor ADMIN = jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper json;

    private long crear(String ruta, String body) throws Exception {
        String r = mvc.perform(post(ruta).with(ADMIN).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn().getResponse().getContentAsString();
        return json.readTree(r).get("id").asLong();
    }

    @Test
    void productos_crudCompleto() throws Exception {
        long id = crear("/api/productos", "{\"nombre\":\"Pan de molde\",\"categoria\":\"Panadería\",\"precio\":8.90,\"stock\":25}");

        mvc.perform(get("/api/productos").with(ADMIN)).andExpect(status().isOk());
        mvc.perform(get("/api/productos/" + id).with(ADMIN)).andExpect(jsonPath("$.nombre").value("Pan de molde"));
        mvc.perform(put("/api/productos/" + id).with(ADMIN).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Pan de molde\",\"categoria\":\"Panadería\",\"precio\":9.50,\"stock\":30}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precio").value(9.5));
        mvc.perform(post("/api/productos").with(ADMIN).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"x\",\"precio\":-1,\"stock\":1}"))
                .andExpect(status().isBadRequest());
        mvc.perform(delete("/api/productos/" + id).with(ADMIN)).andExpect(status().isNoContent());
        mvc.perform(get("/api/productos/" + id).with(ADMIN)).andExpect(status().isNotFound());
        mvc.perform(delete("/api/productos/" + id).with(ADMIN)).andExpect(status().isNotFound());
    }

    @Test
    void clientes_crudCompletoYDniDuplicado() throws Exception {
        long id = crear("/api/clientes", "{\"nombre\":\"Luis\",\"apellido\":\"Ramos\",\"dni\":\"70999888\"}");

        mvc.perform(get("/api/clientes").with(ADMIN)).andExpect(status().isOk());
        mvc.perform(get("/api/clientes/" + id).with(ADMIN)).andExpect(jsonPath("$.dni").value("70999888"));
        mvc.perform(post("/api/clientes").with(ADMIN).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Otro\",\"dni\":\"70999888\"}"))
                .andExpect(status().isConflict());
        mvc.perform(put("/api/clientes/" + id).with(ADMIN).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Luis\",\"apellido\":\"Ramos Díaz\",\"dni\":\"70999888\"}"))
                .andExpect(jsonPath("$.apellido").value("Ramos Díaz"));
        mvc.perform(delete("/api/clientes/" + id).with(ADMIN)).andExpect(status().isNoContent());
        mvc.perform(get("/api/clientes/" + id).with(ADMIN)).andExpect(status().isNotFound());
    }

    @Test
    void categorias_crudCompletoYNombreDuplicado() throws Exception {
        long id = crear("/api/categorias", "{\"nombre\":\"Golosinas test\",\"descripcion\":\"Dulces\"}");

        mvc.perform(get("/api/categorias").with(ADMIN)).andExpect(status().isOk());
        mvc.perform(get("/api/categorias/" + id).with(ADMIN)).andExpect(status().isOk());
        mvc.perform(post("/api/categorias").with(ADMIN).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"golosinas TEST\"}"))
                .andExpect(status().isConflict());
        mvc.perform(put("/api/categorias/" + id).with(ADMIN).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Golosinas test\",\"descripcion\":\"Dulces y chocolates\"}"))
                .andExpect(jsonPath("$.descripcion").value("Dulces y chocolates"));
        mvc.perform(put("/api/categorias/999999").with(ADMIN).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Nada\"}"))
                .andExpect(status().isNotFound());
        mvc.perform(delete("/api/categorias/" + id).with(ADMIN)).andExpect(status().isNoContent());
        mvc.perform(delete("/api/categorias/" + id).with(ADMIN)).andExpect(status().isNotFound());
    }
}
