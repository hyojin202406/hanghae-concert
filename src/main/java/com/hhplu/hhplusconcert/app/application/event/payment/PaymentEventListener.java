package com.hhplu.hhplusconcert.app.application.event.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplu.hhplusconcert.app.application.service.outbox.OutboxService;
import com.hhplu.hhplusconcert.app.domain.outbox.entity.Outbox;
import com.hhplu.hhplusconcert.app.domain.payment.PaymentMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final PaymentMessageProducer paymentMessageProducer;
    private final OutboxService outBoxService;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void savePaymentOutBox(PaymentSuccessEvent event) {
        try {
            log.info("savePaymentOutBox : {}", event);
            String payload = objectMapper.writeValueAsString(event);
            Outbox outbox = Outbox.createOutbox("payment", event.getEventKey(), payload);
            outBoxService.save(outbox);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert event to JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process reservation event", e);
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePayment(PaymentSuccessEvent event) {
        // 카프카 발행
        log.info("handlePayment : {}", event);
        paymentMessageProducer.send("payment-topic", event.getEventKey(), event);
    }
}