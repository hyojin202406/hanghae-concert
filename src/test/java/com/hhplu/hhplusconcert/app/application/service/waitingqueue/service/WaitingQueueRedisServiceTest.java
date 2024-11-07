package com.hhplu.hhplusconcert.app.application.service.waitingqueue.service;

import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class WaitingQueueRedisServiceTest {
    @Autowired
    private WaitingQueueRedisService waitingQueueRedisService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String WAITING_QUEUE_KEY = "waitingUserQueue";
    private static final String ACTIVE_QUEUE_KEY = "activeUserQueue"; // 활성 사용자 대기열 (List)

    @BeforeEach
    void 활성_유저수_증가() {
        for (Long i = 0L; i < 10; i++) {
            // Given
            Long userId = i;
            User user = User.builder()
                    .id(userId)
                    .name("userId")
                    .build();

            // When
            String token = waitingQueueRedisService.createWaitingQueueToken(user, 1L);
        }
    }

    @Test
    void createWaitingQueueTokenTest() {
        // Given
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .name("userId")
                .build();

        // When
        String token = waitingQueueRedisService.createWaitingQueueToken(user, 1L);

        // Then
        // activeQueueSize를 createWaitingQueueToken 실행 후 확인
        Long activeQueueSize = redisTemplate.opsForList().size(ACTIVE_QUEUE_KEY);

        if (activeQueueSize != null && activeQueueSize < 10) {
            // ACTIVE_QUEUE_KEY에 토큰이 리스트로 추가된 경우
            List<Object> set = redisTemplate.opsForList().range(ACTIVE_QUEUE_KEY, 0, -1);

            // 대기열에 추가된 토큰이 있는지 확인
            assertNotNull(set);
            assertTrue(set.contains(token));

            // ACTIVE_QUEUE_KEY에서 해당 토큰 조회
            List<Object> redisKeyValues = redisTemplate.opsForList().range(ACTIVE_QUEUE_KEY, 0, -1);
            // 리스트에서 특정 토큰을 찾기
            assertNotNull(redisKeyValues);
            assertTrue(redisKeyValues.contains(token)); // 토큰이 리스트에 존재하는지 확인
        } else {
            // ACTIVE_QUEUE_KEY에 데이터가 10 이상이면 WAITING_QUEUE_KEY에 토큰이 저장됨
            Set<Object> set = redisTemplate.opsForZSet().range(WAITING_QUEUE_KEY, 0, -1);

            // 대기열에 추가된 토큰이 있는지 확인
            assertNotNull(set);
            assertTrue(set.contains(token));

            // 저장된 값이 타임스탬프 형식으로 존재하는지 확인
            Double timestamp = redisTemplate.opsForZSet().score(WAITING_QUEUE_KEY, token);
            assertNotNull(timestamp);
            assertTrue(timestamp > 0); // 타임스탬프가 양수 값이어야 함
        }
    }

    @Test
    void 대기열_순번_조회_성공() {
        // Given
        // 가장 우선순위가 높은 토큰을 꺼내기 위해 score가 가장 작은 값을 조회
        Set<Object> set = redisTemplate.opsForZSet().rangeByScore(WAITING_QUEUE_KEY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0, 1);
        if (set.isEmpty()) {
            log.warn("대기열에 데이터가 없습니다.");
            return;
        }

        String queueToken = set.iterator().next().toString();

        // When
        int result = waitingQueueRedisService.getUserPosition(queueToken);
        log.info("result: {}", result);

        // Then
        assertEquals(1, result, "대기열 순번은 1이어야 합니다.");
    }

}