package com.hhplu.hhplusconcert.app.application.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class WaitingQueueStatusScheduler {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String WAITING_QUEUE_KEY = "waitingUserQueue"; // Sorted Set 키 (우선순위 대기열)
    private static final String ACTIVE_QUEUE_KEY = "activeUserQueue"; // 활성 사용자 대기열 (List)

    // 스케줄러로 활성 유저 대기열에 10명씩 추가
    @Scheduled(cron = "0 0/1 * * * *")
    public void activateUsers() {
        log.info("스케줄러 시작");
        Set<Object> tokens = redisTemplate.opsForZSet().range(WAITING_QUEUE_KEY, 0, 9);
        log.info("tokens : {}", tokens);
        if (tokens != null && !tokens.isEmpty()) {
            for (Object token : tokens) {
                // 활성 사용자 대기열에 추가
                redisTemplate.opsForList().rightPush(ACTIVE_QUEUE_KEY, token);
                // 대기열에서 제거
                redisTemplate.opsForZSet().remove(WAITING_QUEUE_KEY, token);
            }
        }

        // TTL 설정: 3분, 한번만 설정하기 위해 조건 추가
        if (redisTemplate.getExpire(ACTIVE_QUEUE_KEY) == -1) {
            redisTemplate.expire(ACTIVE_QUEUE_KEY, 180, TimeUnit.SECONDS);
        }

    }
}