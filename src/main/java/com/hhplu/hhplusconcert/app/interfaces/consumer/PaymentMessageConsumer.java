package com.hhplu.hhplusconcert.app.interfaces.consumer;

import com.hhplu.hhplusconcert.app.application.service.outbox.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentMessageConsumer {

    private final OutboxService outboxService;

    @KafkaListener(topics = "payment-topic", groupId = "consumerGroupId")
    public void listener(ConsumerRecord<String, String> data) {
        final String eventKey = data.key();
        outboxService.markPublished(eventKey);
    }
}
