package com.hhplu.hhplusconcert.app.domain.waitingqueue.repository;

import com.hhplu.hhplusconcert.app.domain.waitingqueue.entity.WaitingQueue;

import java.util.Optional;

public interface WaitingQueueRepository {
    WaitingQueue token(WaitingQueue queue);

    Optional<WaitingQueue> getToken(String queueToken);

    Long getLastActiveId();

    void deactivateStatus();

    void activateStatus();

    Optional<WaitingQueue> isValidToken(String token);
}
