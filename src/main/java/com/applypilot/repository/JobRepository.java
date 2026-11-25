package com.applypilot.repository;

import com.applypilot.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {

    Page<Job> findByUserId(UUID userId, Pageable pageable);

    Optional<Job> findByIdAndUserId(UUID id, UUID userId);

    @Query("""
    SELECT j FROM Job j
    WHERE j.user.id = :userId
    AND LOWER(j.company) = LOWER(COALESCE(:company, j.company))
    AND LOWER(j.role) = LOWER(COALESCE(:role, j.role))
""")
    Page<Job> filterJobs(
            @Param("userId") UUID userId,
            @Param("company") String company,
            @Param("role") String role,
            Pageable pageable
    );
}