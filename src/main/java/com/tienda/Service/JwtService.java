package com.tienda.Service;

import com.tienda.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtService {

    public static final long EXPIRATION_SECONDS = 86400L;

    private final JwtEncoder jwtEncoder;

    public String generarToken(String username, String rol) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("tienda-api")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRATION_SECONDS))
                .subject(username)
                .claim("rol", rol)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public String generarToken(Usuario usuario) {
        return generarToken(usuario.getUsername(), usuario.getRol());
    }
}
