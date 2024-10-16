package com.hhplu.hhplusconcert.app.application.scheduler;

import com.hhplu.hhplusconcert.app.domain.watingqueue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingQueueStatusScheduler {

    private final WaitingQueueRepository queueRepository;

    @Scheduled(cron = "0 0/10 * * * *")
    public void updateQueueStatus() {
        queueRepository.deactivateStatus();
        queueRepository.activateStatus();
    }
}