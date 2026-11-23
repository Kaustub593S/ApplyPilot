package com.applypilot.controller;

import com.applypilot.dto.profile.ExperienceResponse;
import com.applypilot.dto.profile.ProfileResponse;
import com.applypilot.dto.profile.ProjectResponse;
import com.applypilot.dto.profile.UpdateProfileRequest;
import com.applypilot.services.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile()
    {
        return ResponseEntity.ok(profileService.getProfile());
    }

    @GetMapping("/experiences")
    public ResponseEntity<List<ExperienceResponse>> getProfileExperiences()
    {
        return ResponseEntity.ok(profileService.getExperiences());
    }
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectResponse>> getProfileProjects()
    {
        return ResponseEntity.ok(profileService.getProjects());
    }
    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest req)
    {
        return ResponseEntity.ok(profileService.updateProfile(req));
    }
}

