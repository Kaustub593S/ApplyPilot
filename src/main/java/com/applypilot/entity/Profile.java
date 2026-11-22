package com.applypilot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="profiles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    User user;

    @Column(nullable = false)
    String name;

    @Size(max = 10,min=10)
            //regex
    String phoneNo;

    @ElementCollection
    @CollectionTable(
            name = "profile_links",
            joinColumns = @JoinColumn(name = "profile_id")
    )
    @Column(name = "link")
    private List<String> links = new ArrayList<>();


    String summary;

}
