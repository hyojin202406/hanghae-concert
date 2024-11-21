package com.hhplu.hhplusconcert.app.application.scheduler;

import com.hhplu.hhplusconcert.app.application.service.outbox.OutboxService;
import com.hhplu.hhplusconcert.app.domain.outbox.entity.Outbox;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventScheduler {

    private final OutboxService outBoxService;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(cron = "0 0/5 * * * *")
    @Transactional
    public void republishMessage() {
        log.info("republishMessage start");

        List<Outbox> outBoxList = outBoxService.findFailMessages();

        outBoxList.forEach(outbox -> {
            try {
                kafkaTemplate.send(outbox.getEventType() + "-topic", outbox.getPayload()).get(); // 동기적 처리
                outbox.publishedStaus();
                log.info("Message published: {}", outbox.getPayload());
            } catch (Exception e) {
                log.error("Message publish failed: {}", outbox.getPayload(), e);
            }
        });

        log.info("republishMessage end");
    }
}
