package com.applypilot.services;

import com.applypilot.repository.ProfileRepository;
import com.applypilot.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private  final ProfileRepository profileRepository;
}
