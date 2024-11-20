package com.hhplu.hhplusconcert.app.infrastructure.event;

import com.hhplu.hhplusconcert.app.infrastructure.event.dto.Message;
import com.hhplu.hhplusconcert.common.config.TestContainersTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class TestProducerTest extends  TestContainersTest{

    @Autowired
    private TestProducer testProducer;

    @Test
    void testProducer() {
        // Given
        Message message = new Message("name", "message");

        // When & Then
        testProducer.send("test-topic", message);
    }

}