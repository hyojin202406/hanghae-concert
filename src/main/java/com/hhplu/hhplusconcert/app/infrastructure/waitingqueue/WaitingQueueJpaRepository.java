package com.hhplu.hhplusconcert.app.infrastructure.waitingqueue;

import com.hhplu.hhplusconcert.app.domain.watingqueue.WaitingQueueStatus;
import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WaitingQueueJpaRepository extends JpaRepository<WaitingQueue, Long> {

    Optional<WaitingQueue> findByQueueToken(String any);

    Optional<WaitingQueue> findFirstByQueueStatusOrderByIssuedAtDesc(WaitingQueueStatus queueStatus);

    List<WaitingQueue> findByExpiredAtBefore(LocalDateTime now);

    List<WaitingQueue> findTop10ByQueueStatusOrderByIssuedAtAsc(WaitingQueueStatus waitingQueueStatus);
}
