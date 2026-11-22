package com.applypilot.dto.generatedAnswers;

import jakarta.validation.constraints.NotBlank;

public record UpdateAnswerRequest(
        @NotBlank
        String answer
) {

}

