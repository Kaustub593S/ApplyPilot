package com.applypilot.dto.job;


import tools.jackson.databind.JsonNode;

import java.time.Instant;
import java.util.UUID;

public record JobResponse(
        UUID id,
        String company,
        String role,
        String url,
        JsonNode analysisJson,
        Instant createdAt
) {

}
