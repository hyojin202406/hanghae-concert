package com.hhplu.hhplusconcert.app.application.scheduler;

import com.hhplu.hhplusconcert.app.application.service.waitingqueue.WaitingQueueRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WaitingQueueStatusScheduler {

    private final WaitingQueueRedisService waitingQueueRedisService;

    // 스케줄러로 활성 유저 대기열에 10명씩 추가
    @Scheduled(cron = "0 0/10 * * * *")
    public void activateUsers() {
        for (int i = 0; i < 10; i++) {
            ZSetOperations.TypedTuple<Object> tokenWithScore = waitingQueueRedisService.popMinFromWaitingQueue();
            if (tokenWithScore != null) {
                Object token = tokenWithScore.getValue();
                waitingQueueRedisService.moveToActiveQueue(token);
            } else {
                break; // 더 이상 대기열에 사용자가 없으면 중단
            }
        }

        waitingQueueRedisService.setActiveQueueTTL();
    }

}