package com.applypilot.repository;

import com.applypilot.entity.Profile;
import com.applypilot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    List<Profile> findAllByUser(User user);
}
