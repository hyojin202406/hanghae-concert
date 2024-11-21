package com.hhplu.hhplusconcert.app.interfaces.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentMessageConsumer implements MessageConsumer{

    @Override
    @KafkaListener(topics = "payment-topic", groupId = "consumerGroupId")
    public void listener(String data) {
        log.info("payment-topic data: {}", data);
    }
}
