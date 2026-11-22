package com.applypilot.dto.auth;

public record AuthResponse(
        String token,
        UserResponse user
) {
}
