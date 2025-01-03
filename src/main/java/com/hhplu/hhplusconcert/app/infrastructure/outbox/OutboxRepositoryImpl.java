package com.hhplu.hhplusconcert.app.infrastructure.outbox;

import com.hhplu.hhplusconcert.app.domain.outbox.OutboxStatus;
import com.hhplu.hhplusconcert.app.domain.outbox.entity.Outbox;
import com.hhplu.hhplusconcert.app.domain.outbox.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {

    private final OutboxJpaRepository outboxJpaRepository;

    @Override
    public void save(Outbox outbox) {
        outboxJpaRepository.save(outbox);
    }

    @Override
    public List<Outbox> findByOutboxStatus(OutboxStatus outboxStatus) {
        return outboxJpaRepository.findByOutboxStatus(outboxStatus);
    }

    @Override
    public List<Outbox> findByEventKey(String eventKey) {
        return outboxJpaRepository.findByEventKey(eventKey);
    }


}
