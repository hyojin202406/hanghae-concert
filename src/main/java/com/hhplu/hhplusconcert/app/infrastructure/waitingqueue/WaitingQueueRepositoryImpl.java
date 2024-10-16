package com.hhplu.hhplusconcert.app.infrastructure.waitingqueue;

import com.hhplu.hhplusconcert.app.domain.watingqueue.WaitingQueueStatus;
import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;
import com.hhplu.hhplusconcert.app.domain.watingqueue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    }

    @Override
    public void activateStatus() {

    }
}
