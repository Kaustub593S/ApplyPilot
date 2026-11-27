package com.applypilot.services;

import com.applypilot.dto.auth.AuthResponse;
import com.applypilot.dto.auth.LoginRequest;
import com.applypilot.dto.auth.SignupRequest;
import com.applypilot.entity.RefreshToken;
import com.applypilot.entity.User;
import com.applypilot.enums.Role;
import com.applypilot.exceptions.EmailAlreadyUsedException;
import com.applypilot.exceptions.InvalidCredentialsException;
import com.applypilot.repository.RefreshTokenRepository;
import com.applypilot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)          // 1
class AuthServiceTest {

    @Mock
    UserRepository userRepository;     // 2
    @Mock
    RefreshTokenRepository refreshTokenRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock JwtService jwtService;

    @InjectMocks
    AuthService authService;    // 3

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authService, "refreshDays", 7L);   // 4
    }

    @Test
    void login_wrongPassword_throwsInvalidCredentials() {   // 5
        // Arrange
        User user = User.builder().email("a@test.com").passwordHash("hash").role(Role.USER).build();
        when(userRepository.findUsersByEmail("a@test.com")).thenReturn(Optional.of(user));   // 6
        when(passwordEncoder.matches("wrong", "hash")).thenReturn(false);

        // Act + Assert
        assertThatThrownBy(() -> authService.login(new LoginRequest("a@test.com", "wrong")))   // 7
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Invalid email or password");

        verify(refreshTokenRepository, never()).save(any());   // 8
    }
    @Test
    void register_duplicateUser_throwsAndSavesNothing()
    {
        when(userRepository.existsUserByEmail("sidhi2769@gmail.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(new SignupRequest("sidhi2769@gmail.com", "StrongPass123")))
                .isInstanceOf(EmailAlreadyUsedException.class);

        verify(userRepository, never()).save(any());
    }
    @Test
    void login_success()
    {
        User user=User.builder().email("kaus2511@gmail.com").passwordHash("hash").role(Role.USER).build();
        when(userRepository.findUsersByEmail("kaus2511@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("right","hash")).thenReturn(true);
        when(jwtService.generateAccessToken(user)).thenReturn("access-token");
        when(jwtService.getExpirySeconds()).thenReturn(900L);

        AuthResponse res = authService.login(new LoginRequest("kaus2511@gmail.com", "right"));


        assertThat(res.accessToken()).isEqualTo("access-token");
        assertThat(res.refreshToken()).isNotBlank();
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }
}