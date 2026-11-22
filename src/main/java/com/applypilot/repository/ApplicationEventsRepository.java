package com.applypilot.repository;

import com.applypilot.entity.ApplicationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationEventsRepository extends JpaRepository<ApplicationEvent, UUID> {
    List<ApplicationEvent> findByApplication_IdOrderByCreatedAtAsc(UUID applicationId);
}
