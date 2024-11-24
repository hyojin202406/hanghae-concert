package com.hhplu.hhplusconcert.app.kafka;

import com.hhplu.hhplusconcert.app.application.event.payment.PaymentSuccessEvent;
import com.hhplu.hhplusconcert.app.application.event.reservation.ReservationSuccessEvent;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationMessageProducer;
import com.hhplu.hhplusconcert.common.config.TestContainersTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
class ReservationProducerConsumerTest extends TestContainersTest {

    @Autowired
    private ReservationMessageProducer reservationMessageProducer;

    private String receivedMessage;

    @Test
    void testProducerAndConsumer() {
        // Given
        ReservationSuccessEvent message = new ReservationSuccessEvent(1L, 10L);

        // When
        reservationMessageProducer.send("reservation-test-topic", UUID.randomUUID().toString(), message);

        // then
        await().atMost(ofSeconds(10))
                .untilAsserted(() -> {
                    assertThat(receivedMessage).contains("1").contains("10");
                });
    }

    @KafkaListener(topics = "reservation-test-topic", groupId = "consumerGroupId")
    public void consume(String message) {
        receivedMessage = message;
    }
}