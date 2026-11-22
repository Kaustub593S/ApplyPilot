package com.applypilot.dto.auth;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SignupRequest(
        @NotNull
        String name,
        @NotNull
        String phoneNo,
        List<String>links
) {
}
