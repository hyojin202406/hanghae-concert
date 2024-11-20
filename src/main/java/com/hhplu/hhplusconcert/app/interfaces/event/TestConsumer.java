package com.hhplu.hhplusconcert.app.interfaces.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestConsumer {

    @KafkaListener(topics = "topic", groupId = "consumerGroupId")
    public void listener(String data) {
        log.info("kafka groupId : consumerGroupId. data: {}", data);
    }
}