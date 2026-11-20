
package com.tienda.integracion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ErroresFormatoIntegracionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginSinCredencialesDevuelveProblemDetail()
            throws Exception {

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))

            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.title").exists())
            .andExpect(jsonPath("$.detail")
                    .value("Faltan credenciales"));
    }

    @Test
    void loginIncorrectoDevuelveProblemDetail()
            throws Exception {

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "username": "usuario_inexistente_prueba",
                      "password": "password_incorrecta"
                    }
                    """))

            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status").value(401))
            .andExpect(jsonPath("$.title").exists())
            .andExpect(jsonPath("$.detail").exists());
    }
}
