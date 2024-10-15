package com.hhplu.hhplusconcert.application.service;

import com.hhplu.hhplusconcert.domain.user.User;
import com.hhplu.hhplusconcert.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User user(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user;
    }
}
