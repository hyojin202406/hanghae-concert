package com.hhplu.hhplusconcert.app.infrastructure.queue;

import com.hhplu.hhplusconcert.app.domain.queue.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<Queue, Long> {

    Optional<Queue> findByQueueToken(String any);
}
