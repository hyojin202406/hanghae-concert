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
    public WaitingQueue getToken(String queueToken) {
        return waitingQueueJpaRepository.findByQueueToken(queueToken).orElseThrow(() -> new IllegalArgumentException("대기열 토큰을 찾을 수 없습니다."));
    }

    @Override
    public Long getLastActiveId() {
        return waitingQueueJpaRepository.findFirstByQueueStatusOrderByIssuedAtDesc(WaitingQueueStatus.ACTIVE)
                .map(WaitingQueue::getId)
                .orElse(0L); // 값이 존재하지 않으면 0 반환
    }

    @Override
    public void deactivateStatus() {
        LocalDateTime now = LocalDateTime.now();
        List<WaitingQueue> expiredQueues = waitingQueueJpaRepository.findByExpiredAtBefore(now);

        for (WaitingQueue queue : expiredQueues) {
            queue.changeWaitingQueueStatus(WaitingQueueStatus.EXPIRED);
        }

        waitingQueueJpaRepository.saveAll(expiredQueues); // 모든 변경 사항 저장
    }

    @Override
    public void activateStatus() {
        List<WaitingQueue> waitingQueues = waitingQueueJpaRepository.findTop10ByQueueStatusOrderByIssuedAtAsc(WaitingQueueStatus.WAITING);

        for (WaitingQueue queue : waitingQueues) {
            queue.changeWaitingQueueStatus(WaitingQueueStatus.ACTIVE);
        }

        waitingQueueJpaRepository.saveAll(waitingQueues); // 모든 변경 사항 저장
    }

    @Override
    public Optional<WaitingQueue> isValidToken(String queueToken) {
        return waitingQueueJpaRepository.findByQueueToken(queueToken);
    }
}
