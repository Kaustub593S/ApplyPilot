package com.applypilot.entity;

import com.applypilot.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="applications")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @OneToOne
    Job job;

    @Enumerated(EnumType.STRING)
    Status status;

    String resumeUrl;

    String notes;

    Instant appliedAt;


}
