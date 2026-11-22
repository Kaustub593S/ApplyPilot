package com.applypilot.repository;

import com.applypilot.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Optional<Project> findProjectByTitle(String title);
    List<Project> findAllByProfile_Id(UUID profileId);

}
