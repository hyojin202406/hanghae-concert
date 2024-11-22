package com.hhplu.hhplusconcert.app.domain.outbox.repository;

import com.hhplu.hhplusconcert.app.domain.outbox.OutboxStatus;
import com.hhplu.hhplusconcert.app.domain.outbox.entity.Outbox;

import java.util.List;

public interface OutboxRepository {
    void save(Outbox outbox);

    List<Outbox> findByOutboxStatus(OutboxStatus outboxStatus);

    List<Outbox> findByEventKey(String eventKey);
}
