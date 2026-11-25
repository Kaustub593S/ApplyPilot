package com.applypilot.dto.auth;

public record AuthResponse(
        String accesstoken,
        String refreshToken,
        String tokenType,
       long expiresInSeconds
) {

}