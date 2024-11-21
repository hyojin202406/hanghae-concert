package com.hhplu.hhplusconcert.app.domain.outbox.repository;

import com.hhplu.hhplusconcert.app.domain.outbox.entity.Outbox;

import java.util.List;

public interface OutboxRepository {
    void save(Outbox outbox);

    List<Outbox> findByPayload(String data);
}
