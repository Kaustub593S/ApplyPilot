package com.applypilot.dto.profile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateProfileRequest(
        @NotBlank
        String fullName,
        String phone,
        String summary,
        List<String> links,
        @Valid
        List<ExperienceRequest> experiences,
        @Valid
        List<ProjectRequest> projects
) {

}
