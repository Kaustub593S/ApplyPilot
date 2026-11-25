package com.applypilot.dto.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SignupRequest(
        @Email
        @NotBlank
        String email,
        @Size(max = 72, min = 8)
        String password
) {
}
