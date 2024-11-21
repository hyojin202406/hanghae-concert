package com.hhplu.hhplusconcert.app.kafka;

import com.hhplu.hhplusconcert.app.application.event.payment.PaymentSuccessEvent;
import com.hhplu.hhplusconcert.app.domain.payment.PaymentMessageProducer;
import com.hhplu.hhplusconcert.common.config.TestContainersTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.testcontainers.junit.jupiter.Testcontainers;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
class PaymentProducerConsumerTest extends TestContainersTest {

    @Autowired
    private PaymentMessageProducer paymentMessageProducer;

    private String receivedMessage;

    @Test
    void testProducerAndConsumer() {
        // Given
        PaymentSuccessEvent message = new PaymentSuccessEvent(1L, 10L);

        // When
        paymentMessageProducer.send("payment-test-topic", message);

        // then
        await().atMost(ofSeconds(10))
                .untilAsserted(() -> {
                    assertThat(receivedMessage).contains("1").contains("10");
                });
    }

    @KafkaListener(topics = "payment-test-topic", groupId = "consumerGroupId")
    public void consume(String message) {
        receivedMessage = message;
    }
}