package com.applypilot.dto.auth;

import com.applypilot.enums.Role;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        Role role,
        Instant createdAt
) {
}
