package com.applypilot.dto.application;


import com.applypilot.dto.applicationEvents.ApplicationEventResponse;
import com.applypilot.dto.generatedAnswers.GeneratedAnswerResponse;
import com.applypilot.dto.job.JobResponse;
import com.applypilot.enums.Status;

import java.util.List;
import java.util.UUID;

public record ApplicationDetailResponse(
        UUID id,
        JobResponse job,
        Status status,
        String notes,
        List<ApplicationEventResponse> events,
        List<GeneratedAnswerResponse> answers
) {

}

