package com.applypilot.repository;

import com.applypilot.entity.GeneratedAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GeneratedAnswerRepository extends JpaRepository<GeneratedAnswer, UUID> {
    List<GeneratedAnswer> findByApplication_Id(UUID applicationId);
}
