package com.hhplu.hhplusconcert.app.application.service.waitingqueue.service;

import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitingQueueRedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String WAITING_QUEUE_KEY = "waitingUserQueue"; // Sorted Set 키 (우선순위 대기열)
    private static final String ACTIVE_QUEUE_KEY = "activeUserQueue"; // 활성 사용자 대기열 (List)

    public String createWaitingQueueToken(User user) {
        // 유저의 토큰 생성
        user.generateToken();
        String token = user.getToken();
        long timestamp = System.currentTimeMillis();

        // activeUserQueue의 데이터 개수 확인
        Long activeQueueSize = redisTemplate.opsForList().size(ACTIVE_QUEUE_KEY);

        // activeUserQueue에 데이터가 10 미만일 경우, 데이터 추가
        if (activeQueueSize != null && activeQueueSize < 10) {
            redisTemplate.opsForList().rightPush(ACTIVE_QUEUE_KEY, token);
        } else {
            redisTemplate.opsForZSet().add(WAITING_QUEUE_KEY, token, timestamp);
        }

        return token;
    }

    /**
     * 사용자의 대기 순번 확인
     * @param queueToken
     * @return
     */
    public int getUserPosition(String queueToken) {
        Long rank = redisTemplate.opsForZSet().rank(WAITING_QUEUE_KEY, queueToken);
        return rank != null ? rank.intValue() + 1 : -1; // 순번을 1부터 시작하도록 반환
    }
}