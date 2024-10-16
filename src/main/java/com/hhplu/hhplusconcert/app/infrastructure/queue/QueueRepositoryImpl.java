package com.hhplu.hhplusconcert.app.infrastructure.queue;

import com.hhplu.hhplusconcert.app.domain.queue.QueueStatus;
import com.hhplu.hhplusconcert.app.domain.queue.entity.Queue;
import com.hhplu.hhplusconcert.app.domain.queue.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {
    private final QueueJpaRepository queueJpaRepository;

    @Override
    public Queue token(Queue queue) {
        return queueJpaRepository.save(queue);
    }

    @Override
    public Queue getToken(String queueToken) {
        return queueJpaRepository.findByQueueToken(queueToken).orElseThrow(() -> new IllegalArgumentException("대기열 토큰을 찾을 수 없습니다."));
    }

    @Override
    public Long getLastActiveId() {
        return queueJpaRepository.findFirstByQueueStatusOrderByIssuedAtDesc(QueueStatus.ACTIVE)
                .map(Queue::getId)
                .orElse(0L); // 값이 존재하지 않으면 0 반환
    }
}
