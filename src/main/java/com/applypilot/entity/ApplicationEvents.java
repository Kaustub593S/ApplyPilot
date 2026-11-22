package com.applypilot.entity;

import com.applypilot.enums.EventType;
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
@Table(name="application_events")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_id")
    Application application;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    EventType type;

    String message;

    @CreationTimestamp
    Instant createdAt;
}

