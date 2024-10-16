package com.hhplu.hhplusconcert.app.domain.watingqueue.repository;

import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;

public interface WaitingQueueRepository {
    WaitingQueue token(WaitingQueue queue);

    WaitingQueue getToken(String queueToken);

    Long getLastActiveId();

    void deactivateStatus();

    void activateStatus();
}
