package com.applypilot.dto.application;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateApplicationRequest(
        @NotNull
        UUID jobId
) {}
