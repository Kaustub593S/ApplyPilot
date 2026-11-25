package com.applypilot.services;

import com.applypilot.config.RefreshTokenUtil;
import com.applypilot.dto.auth.AuthResponse;
import com.applypilot.dto.auth.LoginRequest;
import com.applypilot.dto.auth.RefreshRequest;
import com.applypilot.dto.auth.SignupRequest;
import com.applypilot.dto.auth.UserResponse;
import com.applypilot.entity.RefreshToken;
import com.applypilot.entity.User;
import com.applypilot.enums.Role;
import com.applypilot.exceptions.EmailAlreadyUsedException;
import com.applypilot.exceptions.InvalidCredentialsException;
import com.applypilot.repository.RefreshTokenRepository;
import com.applypilot.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${app.jwt.refresh-expiry-days}")
    private long refreshDays;

    // ---------- register ----------
    @Transactional
    public UserResponse register(SignupRequest req) {
        String email = normalize(req.email());

        if (userRepository.existsUserByEmail(email)) {
            throw new EmailAlreadyUsedException("Email already registered");
        }

        User saved = userRepository.save(User.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(req.password()))
                .role(Role.USER)               // always set by the server
                .build());

        return new UserResponse(saved.getId(), saved.getEmail(), saved.getRole(), saved.getCreatedAt());
    }

    // ---------- login ----------
    @Transactional   // writes a refresh token row, so not readOnly
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findUsersByEmail(normalize(req.email()))
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return issueTokens(user);
    }

    // ---------- refresh (with rotation and reuse detection) ----------
    // noRollbackFor: the revokeAllForUser call in the reuse branch must survive the exception
    @Transactional(noRollbackFor = InvalidCredentialsException.class)
    public AuthResponse refresh(RefreshRequest req) {
        RefreshToken stored = refreshTokenRepository
                .findByTokenHash(RefreshTokenUtil.hash(req.refreshToken()))
                .orElseThrow(() -> new InvalidCredentialsException("Invalid refresh token"));

        if (stored.isRevoked()) {
            // an already-rotated token was presented again: assume theft, kill every session
            refreshTokenRepository.revokeAllForUser(stored.getUser().getId());
            throw new InvalidCredentialsException("Invalid refresh token");
        }

        if (stored.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidCredentialsException("Refresh token expired");
        }

        stored.setRevoked(true);               // rotation: the old token is now dead
        return issueTokens(stored.getUser());
    }

    // ---------- logout ----------
    @Transactional
    public void logout(RefreshRequest req) {
        refreshTokenRepository
                .findByTokenHash(RefreshTokenUtil.hash(req.refreshToken()))
                .ifPresent(t -> t.setRevoked(true));   // silent if unknown
    }

    // ---------- helpers ----------
    private AuthResponse issueTokens(User user) {
        String rawRefresh = RefreshTokenUtil.generate();

        refreshTokenRepository.save(RefreshToken.builder()
                .user(user)
                .tokenHash(RefreshTokenUtil.hash(rawRefresh))   // only the hash is stored
                .expiresAt(Instant.now().plus(refreshDays, ChronoUnit.DAYS))
                .revoked(false)
                .build());

        return new AuthResponse(
                jwtService.generateAccessToken(user),
                rawRefresh,
                "Bearer",
                jwtService.getExpirySeconds());
    }

    private String normalize(String email) {
        return email.trim().toLowerCase();
    }
}