package com.hhplu.hhplusconcert.app.domain.queue.repository;

import com.hhplu.hhplusconcert.app.domain.queue.entity.WaitingQueue;

public interface QueueRepository {
    WaitingQueue token(WaitingQueue queue);

    WaitingQueue getToken(String queueToken);

    Long getLastActiveId();
}
