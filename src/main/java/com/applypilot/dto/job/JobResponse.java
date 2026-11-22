package com.applypilot.dto.job;


import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record JobResponse(
        UUID id,
        String company,
        String role,
        String url,
        Map<String,Object> analysisJson,
        Instant createdAt
) {

}
