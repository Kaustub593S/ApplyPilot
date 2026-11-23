package com.applypilot.mapper;

import com.applypilot.dto.profile.*;
import com.applypilot.entity.Experience;
import com.applypilot.entity.Profile;
import com.applypilot.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileResponse toProfileResponse(Profile profile);
    List<ExperienceResponse> toExperienceResponses(List<Experience> list);
    List<ProjectResponse> toProjectResponses(List<Project> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profile", ignore = true)
    Experience toExperience(ExperienceRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profile", ignore = true)
    Project toProject(ProjectRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "experiences", ignore = true)
    @Mapping(target = "projects", ignore = true)
    void updateProfileFields(UpdateProfileRequest request, @MappingTarget Profile profile);
}