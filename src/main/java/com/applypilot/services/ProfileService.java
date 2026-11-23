package com.applypilot.services;

import com.applypilot.dto.profile.*;
import com.applypilot.entity.Experience;
import com.applypilot.entity.Profile;
import com.applypilot.entity.Project;
import com.applypilot.mapper.ProfileMapper;
import com.applypilot.repository.ProfileRepository;
import com.applypilot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.applypilot.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;

    @Value("${app.temp-user-id}")   // TEMP: replaced by the logged-in user in Week 2
    private UUID currentUserId;

    @Transactional(readOnly = true)
    public ProfileResponse getProfile() {
        return profileMapper.toProfileResponse(findProfile());
    }

    @Transactional(readOnly = true)
    public List<ExperienceResponse> getExperiences() {
        return profileMapper.toExperienceResponses(findProfile().getExperiences());
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjects() {
        return profileMapper.toProjectResponses(findProfile().getProjects());
    }

    @Transactional
    public ProfileResponse updateProfile(UpdateProfileRequest req) {
        Profile profile = profileRepository.findByUserId(currentUserId)
                .orElseGet(() -> Profile.builder()
                        .user(userRepository.getReferenceById(currentUserId))
                        .build());

        profileMapper.updateProfileFields(req, profile);

        profile.getExperiences().clear();
        if (req.experiences() != null) {
            for (ExperienceRequest e : req.experiences()) {
                Experience exp = profileMapper.toExperience(e);
                exp.setProfile(profile);
                profile.getExperiences().add(exp);
            }
        }

        profile.getProjects().clear();
        if (req.projects() != null) {
            for (ProjectRequest p : req.projects()) {
                Project proj = profileMapper.toProject(p);
                proj.setProfile(profile);
                profile.getProjects().add(proj);
            }
        }

        return profileMapper.toProfileResponse(profileRepository.save(profile));
    }

    private Profile findProfile() {
        return profileRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
    }
}