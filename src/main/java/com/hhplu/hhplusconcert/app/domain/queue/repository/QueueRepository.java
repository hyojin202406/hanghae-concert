package com.hhplu.hhplusconcert.app.domain.queue.repository;

import com.hhplu.hhplusconcert.app.domain.queue.entity.Queue;

public interface QueueRepository {
    Queue token(Queue queue);

    Queue getToken(String queueToken);

    Long getLastActiveId();
}
