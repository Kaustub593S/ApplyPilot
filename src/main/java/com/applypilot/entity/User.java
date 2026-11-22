package com.applypilot.entity;

import com.applypilot.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @CreationTimestamp
    Instant createdAt;
    @Email
    @Column(unique = true,nullable = false)
    String email;

    @Column(nullable = false, length = 60)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;
}
