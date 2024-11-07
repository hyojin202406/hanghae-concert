package com.hhplu.hhplusconcert.app.application.scheduler;

import com.hhplu.hhplusconcert.app.application.service.waitingqueue.service.WaitingQueueRedisService;
import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WaitingQueueStatusSchedulerTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    WaitingQueueRedisService waitingQueueRedisService;

    @Autowired
    WaitingQueueStatusScheduler waitingQueueStatusScheduler;

    private static final String WAITING_QUEUE_KEY = "waitingUserQueue";
    private static final String ACTIVE_QUEUE_KEY = "activeUserQueue";

    @BeforeEach
    void 활성_유저수_증가() {
        for (Long i = 0L; i < 30; i++) {
            // Given
            Long userId = i;
            User user = User.builder()
                    .id(userId)
                    .name("userId")
                    .build();

            // When
            waitingQueueRedisService.createWaitingQueueToken(user);
        }
    }

    @AfterEach
    void clearQueue() {
        redisTemplate.delete(WAITING_QUEUE_KEY);
        redisTemplate.delete(ACTIVE_QUEUE_KEY);
    }

    @Test
    void 대기열_상태를_갱신한다() {
        // When
        waitingQueueStatusScheduler.activateUsers();

        // Then
        List<Object> redisKeyValues = redisTemplate.opsForList().range(ACTIVE_QUEUE_KEY, 0, -1);
        assertNotNull(redisKeyValues);
    }
}