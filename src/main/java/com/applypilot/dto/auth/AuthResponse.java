package com.applypilot.dto.auth;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
       long expiresInSeconds
) {

}