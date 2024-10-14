package com.hhplu.hhplusconcert.application;

import com.hhplu.hhplusconcert.domain.user.UserEntity;
import com.hhplu.hhplusconcert.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserEntity user(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user;
    }
}
