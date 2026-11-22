package com.applypilot.repository;

import com.applypilot.entity.Job;
import com.applypilot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    List<Job> findAllByUser(User user);
    List<Job> findByRole(String role);
    List<Job> findAllByCompany(String company);
    Optional<Job> findById(UUID id);
}
