package com.hhplu.hhplusconcert.app.infrastructure.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplu.hhplusconcert.app.application.event.reservation.ReservationSuccessEvent;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationMessageProducerImpl implements ReservationMessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String kafkaTopic, String eventKey, ReservationSuccessEvent message) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        kafkaTemplate.send(kafkaTopic, eventKey, jsonInString);
    }
}
