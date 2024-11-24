package com.hhplu.hhplusconcert.app.application.service.outbox;

import com.hhplu.hhplusconcert.app.domain.outbox.OutboxStatus;
import com.hhplu.hhplusconcert.app.domain.outbox.entity.Outbox;
import com.hhplu.hhplusconcert.app.domain.outbox.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;

    public void save(Outbox outbox) {
        outboxRepository.save(outbox);
    }

    @Transactional
    public void markPublished(String data) {
        List<Outbox> byPayload = outboxRepository.findByEventKey(data);
        byPayload.forEach(Outbox::publishedStaus);
        log.info("byPayload : {}", byPayload);
    }

    public List<Outbox> findFailMessages() {
        return outboxRepository.findByOutboxStatus(OutboxStatus.INIT);
    }
}
