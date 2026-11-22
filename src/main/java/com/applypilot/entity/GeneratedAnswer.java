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
@Table(name="generated_answers")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeneratedAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_id")
    Application application;

    @Column(nullable = false, length = 1000)
    String question;

    @Column(nullable = false, columnDefinition = "text")
    String answer;

    @Column(nullable = false)
    boolean approved;

    @CreationTimestamp
    @Column(updatable = false)
    Instant createdAt;
}
