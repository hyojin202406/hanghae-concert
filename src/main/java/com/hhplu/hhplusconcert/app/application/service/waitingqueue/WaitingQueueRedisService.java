package com.hhplu.hhplusconcert.app.application.service.waitingqueue;

import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class WaitingQueueRedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String WAITING_QUEUE_KEY = "waitingUserQueue"; // Sorted Set 키 (우선순위 대기열)
    private static final String ACTIVE_QUEUE_KEY = "activeUserQueue"; // 활성 사용자 대기열 (List)

    /**
     * 토큰 생성 및 대기열 저장
     */
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
     */
    public int getUserPosition(String queueToken) {
        Long rank = redisTemplate.opsForZSet().rank(WAITING_QUEUE_KEY, queueToken);
        return rank != null ? rank.intValue() + 1 : -1; // 순번을 1부터 시작하도록 반환
    }

    public void moveToActiveQueue(Object token) {
        redisTemplate.opsForList().rightPush(ACTIVE_QUEUE_KEY, token);
        redisTemplate.opsForZSet().remove(WAITING_QUEUE_KEY, token);
    }

    public void setActiveQueueTTL() {
        Long expire = redisTemplate.getExpire(ACTIVE_QUEUE_KEY);
        if (expire == -1) {
            redisTemplate.expire(ACTIVE_QUEUE_KEY, 30, TimeUnit.MINUTES);
        }
    }

    public ZSetOperations.TypedTuple<Object> popMinFromWaitingQueue() {
        Long size = redisTemplate.opsForZSet().zCard(WAITING_QUEUE_KEY);
        if (size == null || size == 0) {
            return null; // 대기열이 비어 있으면 null 반환
        }
        return redisTemplate.opsForZSet().popMin(WAITING_QUEUE_KEY);
    }

    public void removeActiveToken(@NotNull String queueToken) {
        redisTemplate.opsForZSet().remove(WAITING_QUEUE_KEY, queueToken);
    }
}