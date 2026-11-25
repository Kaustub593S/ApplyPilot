package com.applypilot.dto.profile;

import java.time.LocalDate;
import java.util.List;

public record ExperienceResponse(
        String title,
        String org,
        LocalDate startDate,
        LocalDate endDate,
        List<String> bullets
) {

}
