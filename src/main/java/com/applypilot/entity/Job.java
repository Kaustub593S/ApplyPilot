package com.applypilot.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="jobs")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    String company;
    @Column(nullable = false)
    String role;

    String url;

    @Column(nullable = false, columnDefinition = "text")
    String description;

    @CreationTimestamp
    Instant createdAt;
}