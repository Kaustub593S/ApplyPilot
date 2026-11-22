package com.applypilot.dto.job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateJobRequest(
        @NotBlank
        String company,
        @NotBlank
        String role,
        @Size(max = 1000)
        String url,
        @NotBlank
        String description
) {

}
