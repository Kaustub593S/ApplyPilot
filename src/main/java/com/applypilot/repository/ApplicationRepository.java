package com.applypilot.repository;

import com.applypilot.entity.Application;
import com.applypilot.entity.User;
import com.applypilot.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    
    List<Application> findByUser_Id(UUID userId);
    List<Application> findByUser(User user);
    List<Application> findByStatus(Status status);
}
