package com.applypilot.dto.generatedAnswers;

import java.time.Instant;
import java.util.UUID;

public record GeneratedAnswerResponse(
        UUID id,
        String question,
        String answer,
        boolean approved,
        Instant createdAt
) {

}
