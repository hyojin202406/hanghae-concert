package com.hhplu.hhplusconcert.app.infrastructure.event;

import com.hhplu.hhplusconcert.app.infrastructure.event.dto.Message;
import com.hhplu.hhplusconcert.common.config.TestContainersTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@Testcontainers
//class TestProducerConsumerTest extends TestContainersTest {
class TestProducerConsumerTest {

    @Autowired
    private TestProducer testProducer;

    private final CountDownLatch latch = new CountDownLatch(1);
    private String receivedMessage;

    @Test
    void testProducerAndConsumer() throws InterruptedException {
        // Given
        Message message = new Message("name", "message");

        // When
        testProducer.send("test-topic", message);
        
        // Then
        latch.await(5, TimeUnit.SECONDS); // 5초 대기
        assertThat(receivedMessage).contains("name").contains("message");
    }

    @KafkaListener(topics = "test-topic", groupId = "consumerGroupId")
    public void consume(String message) {
        receivedMessage = message;
        latch.countDown();
    }
}