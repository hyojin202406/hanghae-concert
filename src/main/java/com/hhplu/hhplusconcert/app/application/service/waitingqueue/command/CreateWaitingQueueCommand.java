package com.hhplu.hhplusconcert.app.application.service.waitingqueue.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateWaitingQueueCommand {
    @NotNull
    private String queueToken;

    @NotNull
    private LocalDateTime issuedAt;

    public CreateWaitingQueueCommand(String queueToken, LocalDateTime issuedAt) {
        this.queueToken = queueToken;
        this.issuedAt = issuedAt;
    }
}
