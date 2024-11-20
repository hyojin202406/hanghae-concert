package com.hhplu.hhplusconcert.common.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public abstract class TestContainersTest {
    @DynamicPropertySource
    public static void testProperties(DynamicPropertyRegistry registry) {
        // Kafka 컨테이너의 실제 호스트와 포트를 설정
        registry.add("spring.kafka.bootstrap-servers", () -> TestContainersConfig.getKafkaContainer().getBootstrapServers());

        // Redis 관련 설정
        registry.add("spring.data.redis.host", () -> TestContainersConfig.getRedisContainer().getHost());
        registry.add("spring.data.redis.port", () -> TestContainersConfig.getRedisContainer().getFirstMappedPort());
    }
}
