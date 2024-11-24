package com.hhplu.hhplusconcert.app.interfaces.consumer.dto;

public record MessageSendEvent(
        long id,
        long evenId,
        String eventType,
        String payload,
        String eventStatus
) {
}