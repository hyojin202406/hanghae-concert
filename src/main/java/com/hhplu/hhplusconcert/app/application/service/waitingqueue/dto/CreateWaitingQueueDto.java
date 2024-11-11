package com.hhplu.hhplusconcert.app.application.service.waitingqueue.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateWaitingQueueDto {
    @NotNull
    private String queueToken;

    @NotNull
    private LocalDateTime issuedAt;

    public CreateWaitingQueueDto(String queueToken, LocalDateTime issuedAt) {
        this.queueToken = queueToken;
        this.issuedAt = issuedAt;
    }
}
