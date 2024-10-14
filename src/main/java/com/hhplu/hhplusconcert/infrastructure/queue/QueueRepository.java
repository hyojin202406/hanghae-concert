package com.hhplu.hhplusconcert.infrastructure.queue;

import com.hhplu.hhplusconcert.domain.queue.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueRepository extends JpaRepository<QueueEntity, Long> {

}
