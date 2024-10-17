package com.hhplu.hhplusconcert.app.domain.watingqueue.repository;

import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;

import java.util.Optional;

public interface WaitingQueueRepository {
    WaitingQueue token(WaitingQueue queue);

    WaitingQueue getToken(String queueToken);

    Long getLastActiveId();

    void deactivateStatus();

    void activateStatus();

    Optional<WaitingQueue> isValidToken(String token);
}
