package com.healthy_plate.user.application;

import com.healthy_plate.user.infrastructure.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JpaUserRepository userRepository;

    
}
