package com.hhplu.hhplusconcert.app.infrastructure.queue;

import com.hhplu.hhplusconcert.app.domain.queue.QueueStatus;
import com.hhplu.hhplusconcert.app.domain.queue.entity.WaitingQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<WaitingQueue, Long> {

    Optional<WaitingQueue> findByQueueToken(String any);

    Optional<WaitingQueue> findFirstByQueueStatusOrderByIssuedAtDesc(QueueStatus queueStatus);

}
