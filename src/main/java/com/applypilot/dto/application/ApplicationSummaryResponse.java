package com.applypilot.dto.application;


import com.applypilot.enums.Status;

import java.time.Instant;
import java.util.UUID;

public record ApplicationSummaryResponse(
        UUID id,
        String company,
        String role,
        Status status,
        Instant updatedAt
) {

}
