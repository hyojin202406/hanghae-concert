package com.hhplu.hhplusconcert.app.domain.user.repository;

import com.hhplu.hhplusconcert.app.domain.user.entity.User;

public interface UserRepository {
    User getUser(Long userId);
}
