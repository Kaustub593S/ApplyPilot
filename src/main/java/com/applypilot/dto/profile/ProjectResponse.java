package com.applypilot.dto.profile;

import java.util.List;

public record ProjectResponse(
        String title,
        String description,
        List<String> bullets,
        String url
) {
}
