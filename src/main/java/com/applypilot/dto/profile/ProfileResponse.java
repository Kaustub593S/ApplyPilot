package com.applypilot.dto.profile;

import com.applypilot.entity.Experience;
import com.applypilot.entity.Project;
import com.applypilot.entity.User;
import java.util.List;

public record ProfileResponse(
        String fullName,
        String phone,
        String summary,
        List<String> links,
        List<ExperienceResponse> experiences,
        List<ProjectResponse> projects
) {}
