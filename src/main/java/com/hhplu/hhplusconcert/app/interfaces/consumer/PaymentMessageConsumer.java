package com.hhplu.hhplusconcert.app.interfaces.consumer;

import com.hhplu.hhplusconcert.app.application.service.outbox.OutboxService;
import com.hhplu.hhplusconcert.app.domain.outbox.entity.Outbox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentMessageConsumer implements MessageConsumer{

    private final OutboxService outboxService;

    @Override
    @KafkaListener(topics = "payment-topic", groupId = "consumerGroupId")
    public void listener(String data) {
        log.info("payment-topic data: {}", data);
        outboxService.publishedMessage(data);
    }
}
