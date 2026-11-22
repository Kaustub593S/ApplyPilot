package com.applypilot.repository;

import com.applypilot.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExperianceRepository extends JpaRepository<Experience, UUID> {
    List<Experience> findByProfile_Id(UUID profileId);
}
