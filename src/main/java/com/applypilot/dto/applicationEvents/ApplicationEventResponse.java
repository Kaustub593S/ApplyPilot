package com.applypilot.dto.applicationEvents;

import com.applypilot.enums.EventType;

import java.time.Instant;
import java.util.UUID;

public record ApplicationEventResponse(
        UUID id,
        EventType type,
        String message,
        Instant createdAt
) {

}
