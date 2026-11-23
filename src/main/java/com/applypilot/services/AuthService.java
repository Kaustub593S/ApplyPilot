package com.applypilot.services;

import com.applypilot.dto.auth.SignupRequest;
import com.applypilot.dto.auth.UserResponse;
import com.applypilot.entity.User;
import com.applypilot.enums.Role;
import com.applypilot.exceptions.EmailAlreadyUsedException;
import com.applypilot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse register(SignupRequest req) {
        String email = req.email().trim().toLowerCase();

        if (userRepository.existsUserByEmail(email)) {
            throw new EmailAlreadyUsedException("Email already registered");
        }

        User user = User.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(req.password()))
                .role(Role.USER)
                .createdAt(Instant.now())
                .build();

        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getEmail(), saved.getRole(), saved.getCreatedAt());
    }
}