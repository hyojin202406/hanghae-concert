package com.hhplu.hhplusconcert.app.application.service.token;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenValidationService {

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean isValidToken(String token) {
        List<Object> activeQueue = redisTemplate.opsForList().range("activeUserQueue", 0, -1);

        if (activeQueue != null && activeQueue.contains(token)) {
            return true;
        }
        return false;
    }

}