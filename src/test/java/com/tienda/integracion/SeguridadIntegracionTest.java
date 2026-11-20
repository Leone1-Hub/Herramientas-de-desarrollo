package com.tienda.integracion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SeguridadIntegracionTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper json;
    @Autowired private UsuarioRepository usuarios;

    private String login(String username, String password) throws Exception {
        String body = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return json.readTree(body).get("accessToken").asText();
    }

    private String registrarCajero(String username) throws Exception {
        mvc.perform(post("/api/auth/registro").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"" + username + "\",\"password\":\"Clave123\",\"nombre\":\"Ana\","
                                + "\"email\":\"" + username + "@t.pe\",\"rol\":\"ADMINISTRADOR\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rol").value("CAJERO"));
        return login(username, "Clave123");
    }

    @Test
    void sinToken_401EnFormatoProblemDetail() throws Exception {
        mvc.perform(get("/api/productos"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.detail").value("Se requiere autenticación"));
    }

    @Test
    void login_devuelveTokenBearerYConElTokenSeAccede() throws Exception {
        String body = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"Admin123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn").value(86400))
                .andExpect(jsonPath("$.rol").value("ADMINISTRADOR"))
                .andReturn().getResponse().getContentAsString();
        JsonNode r = json.readTree(body);

        mvc.perform(get("/api/productos").header("Authorization", "Bearer " + r.get("accessToken").asText()))
                .andExpect(status().isOk());
    }

    @Test
    void login_claveIncorrecta401_ySinDatos400() throws Exception {
        mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"mala\"}"))
                .andExpect(status().isUnauthorized());
        mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void tokenAlterado_401() throws Exception {
        String token = login("admin", "Admin123");
        String alterado = token.substring(0, token.length() - 3) + "abc";

        mvc.perform(get("/api/productos").header("Authorization", "Bearer " + alterado))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void cajero_puedeVenderPeroNoAdministrarElCatalogo() throws Exception {
        String cajero = "Bearer " + registrarCajero("cajero_seg");

        mvc.perform(get("/api/productos").header("Authorization", cajero)).andExpect(status().isOk());
        mvc.perform(post("/api/productos").header("Authorization", cajero).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"x\",\"precio\":1,\"stock\":1}"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.detail").value("No tiene permisos para esta operación"));
        mvc.perform(delete("/api/categorias/1").header("Authorization", cajero)).andExpect(status().isForbidden());
    }

    @Test
    void registroConIdDeOtroUsuario_noSobrescribeLaCuenta() throws Exception {
        Long idAdmin = usuarios.findByUsername("admin").orElseThrow().getIdUsuario();

        mvc.perform(post("/api/auth/registro").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idUsuario\":" + idAdmin + ",\"username\":\"intruso\",\"password\":\"Hack123\","
                                + "\"nombre\":\"X\",\"email\":\"intruso@x.pe\"}"))
                .andExpect(status().isCreated());

        assertThat(usuarios.findById(idAdmin).orElseThrow().getUsername()).isEqualTo("admin");
        login("admin", "Admin123");
    }

    @Test
    void frontYEndpointsDeAuth_sonPublicos() throws Exception {
        mvc.perform(get("/login.html")).andExpect(status().isOk());
        mvc.perform(post("/api/auth/registro").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());
    }
}
