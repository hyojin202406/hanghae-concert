package com.hhplu.hhplusconcert.app.application.user.service;

import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import com.hhplu.hhplusconcert.app.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User user(Long userId) {
        return userRepository.getUser(userId);
    }
}
