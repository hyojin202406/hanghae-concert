package com.hhplu.hhplusconcert.app.infrastructure.waitingqueue;

import com.hhplu.hhplusconcert.app.domain.waitingqueue.WaitingQueueStatus;
import com.hhplu.hhplusconcert.app.domain.waitingqueue.entity.WaitingQueue;
import com.hhplu.hhplusconcert.app.domain.waitingqueue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WaitingQueueRepositoryImpl implements WaitingQueueRepository {
    private final WaitingQueueJpaRepository waitingQueueJpaRepository;

    @Override
    public WaitingQueue token(WaitingQueue queue) {
        return waitingQueueJpaRepository.save(queue);
    }

    @Override
    public Optional<WaitingQueue> getToken(String queueToken) {
        return waitingQueueJpaRepository.findByQueueToken(queueToken);
    }

    @Override
    public Long getLastActiveId() {
        return waitingQueueJpaRepository.findFirstByQueueStatusOrderByIssuedAtDesc(WaitingQueueStatus.ACTIVE)
                .map(WaitingQueue::getId)
                .orElse(0L);
    }

    @Override
    public void deactivateStatus() {
        LocalDateTime now = LocalDateTime.now();
        List<WaitingQueue> expiredQueues = waitingQueueJpaRepository.findByExpiredAtBefore(now);
        expiredQueues.forEach(WaitingQueue::changeToExpiredStatus);
        waitingQueueJpaRepository.saveAll(expiredQueues);
    }

    @Override
    public void activateStatus() {
        List<WaitingQueue> waitingQueues = waitingQueueJpaRepository.findTop10ByQueueStatusOrderByIssuedAtAsc(WaitingQueueStatus.WAITING);
        waitingQueues.forEach(WaitingQueue::changeToActiveStatus);
        waitingQueueJpaRepository.saveAll(waitingQueues);
    }

    @Override
    public Optional<WaitingQueue> isValidToken(String queueToken) {
        return waitingQueueJpaRepository.findByQueueToken(queueToken);
    }
}
