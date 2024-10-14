package com.hhplu.hhplusconcert.infrastructure.user;

import com.hhplu.hhplusconcert.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findById(long id);
}
