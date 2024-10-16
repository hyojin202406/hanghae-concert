package com.hhplu.hhplusconcert.app.infrastructure.user;

import com.hhplu.hhplusconcert.app.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    User findById(long id);
}
