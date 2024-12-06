package com.hhplu.hhplusconcert.app.application.service.waitingqueue.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateWaitingQueueDto {
    @NotNull
    private Long userId;

    @NotNull
    private String queueToken;

    @NotNull
    private LocalDateTime issuedAt;

    public CreateWaitingQueueDto(Long userId, String queueToken, LocalDateTime issuedAt) {
        this.userId = userId;
        this.queueToken = queueToken;
        this.issuedAt = issuedAt;
    }
}
