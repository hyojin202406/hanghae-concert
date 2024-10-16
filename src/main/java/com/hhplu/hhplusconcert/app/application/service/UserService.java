package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.user.User;
import com.hhplu.hhplusconcert.app.domain.user.UserRepository;
import com.hhplu.hhplusconcert.app.infrastructure.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User user(Long userId) {
        return userRepository.getUser(userId);
    }

    public BigDecimal rechargePoint(Long userId, BigDecimal point) {
        User user = user(userId);
        if (point.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("충전 금액은 0보다 커야 합니다.");
        }
        user.changePointAmount(point);
        return user.getPointAmount();
    }

    public BigDecimal getPoint(Long userId) {
        User user = user(userId);
        return user.getPointAmount();
    }
}
