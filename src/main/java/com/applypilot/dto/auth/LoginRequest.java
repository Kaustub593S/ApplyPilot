package com.applypilot.dto.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email
        @NotBlank
        String email,
        @Column(nullable = false, length = 60)
        String passwordHash
) {
}
