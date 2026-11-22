package com.applypilot.repository;

import com.applypilot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository <User, UUID>{
    Optional<User> findUsersByEmail(String email);
    boolean existsUserByEmail(String email);
}
