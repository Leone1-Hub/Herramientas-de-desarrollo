package com.tienda.dto;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        String username,
        String rol
) {
    public static LoginResponse of(String token, String username, String rol) {
        return new LoginResponse(token, "Bearer", 86400L, username, rol);
    }
}
