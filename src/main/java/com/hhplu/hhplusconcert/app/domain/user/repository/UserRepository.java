package com.hhplu.hhplusconcert.app.domain.user.repository;

import com.hhplu.hhplusconcert.app.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> getUser(Long userId);
}
