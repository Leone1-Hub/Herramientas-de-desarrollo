package com.tienda.Service;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.tienda.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtServiceTest {

    private static final byte[] SECRETO = "clave-de-pruebas-de-al-menos-32-caracteres!".getBytes(StandardCharsets.UTF_8);

    private final JwtService jwtService = new JwtService(new NimbusJwtEncoder(new ImmutableSecret<>(SECRETO)));
    private final NimbusJwtDecoder decoder = NimbusJwtDecoder
            .withSecretKey(new SecretKeySpec(SECRETO, "HmacSHA256")).macAlgorithm(MacAlgorithm.HS256).build();

    @Test
    void generarToken_incluyeUsuarioRolYExpiracionDe24Horas() {
        Usuario admin = new Usuario("admin", "x", "Admin", null, "a@t.pe", "ADMINISTRADOR");

        Jwt jwt = decoder.decode(jwtService.generarToken(admin));

        assertThat(jwt.getSubject()).isEqualTo("admin");
        assertThat(jwt.getClaimAsString("rol")).isEqualTo("ADMINISTRADOR");
        assertThat(jwt.getClaimAsString("iss")).isEqualTo("tienda-api");
        assertThat(Duration.between(jwt.getIssuedAt(), jwt.getExpiresAt()))
                .isEqualTo(Duration.ofSeconds(JwtService.EXPIRATION_SECONDS));
    }

    @Test
    void tokenFirmadoConOtraClave_esRechazado() {
        byte[] otra = "otra-clave-distinta-de-al-menos-32-caracteres".getBytes(StandardCharsets.UTF_8);
        String ajeno = new JwtService(new NimbusJwtEncoder(new ImmutableSecret<>(otra))).generarToken("admin", "ADMINISTRADOR");

        assertThatThrownBy(() -> decoder.decode(ajeno)).isInstanceOf(JwtException.class);
    }
}
