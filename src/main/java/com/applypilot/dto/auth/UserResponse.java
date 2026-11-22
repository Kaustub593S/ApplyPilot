package com.applypilot.dto.auth;

public record UserResponse(
        Long id,
        String email,
        String name
) {
}
