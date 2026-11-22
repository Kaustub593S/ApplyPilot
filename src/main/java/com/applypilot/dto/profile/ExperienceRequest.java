package com.applypilot.dto.profile;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record ExperienceRequest(
        @NotBlank
        String title,
        @NotBlank
        String org,
        LocalDate startDate,
        LocalDate endDate,
        List<String> bullets
) {

}
