package com.hhplu.hhplusconcert.common.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class TestContainersConfig {

    @Getter
    private static final KafkaContainer kafkaContainer;

    @Getter
    private static final GenericContainer<?> redisContainer;

    static {
        kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
        redisContainer = new GenericContainer<>("redis:latest")
                .withExposedPorts(6379);
    }

    @PostConstruct // 실행 시 컨테이너 시작
    public void startContainers() {
        kafkaContainer.start();
        redisContainer.start();
    }

    @PreDestroy // 종료 시 컨테이너 중단
    public void endContainers() {
        kafkaContainer.stop();
        redisContainer.stop();
    }

}