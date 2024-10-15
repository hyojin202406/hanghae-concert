package com.hhplu.hhplusconcert.infrastructure.queue;

import com.hhplu.hhplusconcert.domain.queue.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueueRepository extends JpaRepository<Queue, Long> {

    Optional<Queue> findByQueueToken(String any);
}
