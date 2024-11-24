package com.hhplu.hhplusconcert.app.interfaces.consumer;

import com.hhplu.hhplusconcert.app.application.service.outbox.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationMessageConsumer {

    private final OutboxService outboxService;

    @KafkaListener(topics = "reservation-topic", groupId = "consumerGroupId")
    public void listener(ConsumerRecord<String, String> data, Acknowledgment acknowledgment, Consumer<String, String> consumer) {
        final String eventKey = data.key();
        outboxService.markPublished(eventKey);
        acknowledgment.acknowledge();
    }

}
