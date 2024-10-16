package com.hhplu.hhplusconcert.app.infrastructure.user;

import com.hhplu.hhplusconcert.app.domain.user.User;
import com.hhplu.hhplusconcert.app.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;


    @Override
    public User getUser(Long userId) {
        return userJpaRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }
}
