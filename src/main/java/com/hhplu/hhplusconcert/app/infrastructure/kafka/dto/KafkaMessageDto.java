package com.hhplu.hhplusconcert.app.infrastructure.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KafkaMessageDto {
    private String name;
    private String message;
}
