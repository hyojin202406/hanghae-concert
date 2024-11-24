package com.hhplu.hhplusconcert.app.application.event.reservation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplu.hhplusconcert.app.application.service.outbox.OutboxService;
import com.hhplu.hhplusconcert.app.domain.outbox.entity.Outbox;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventListener {

    private final ReservationMessageProducer reservationMessageProducer;
    private final OutboxService outBoxService;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveReservationOutBox(ReservationSuccessEvent event) {
        try {
            log.info("saveReservationOutBox: {}", event);
            String payload = objectMapper.writeValueAsString(event);
            Outbox outbox = Outbox.createOutbox("reservation", event.getEventKey(), payload);
            outBoxService.save(outbox);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize event to JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process reservation event", e);
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservation(ReservationSuccessEvent event) {
        // 카프카 발행
        log.info("handleReservation : {}", event);
        reservationMessageProducer.send("reservation-topic", event.getEventKey(), event);
    }
}