package com.hhplu.hhplusconcert.app.application.waitingqueue.command;

import com.hhplu.hhplusconcert.app.domain.watingqueue.WaitingQueueStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetWaitingQueueCommand {

    @NotNull
    private Long userId;

    @NotNull
    private Long id;

    @NotNull
    private WaitingQueueStatus queueStatus;

    @NotNull
    private LocalDateTime issuedAt;

    @NotNull
    private Long lastActiveId;

    public GetWaitingQueueCommand(Long id, Long userId, WaitingQueueStatus queueStatus, LocalDateTime issuedAt, Long lastActiveId) {
        this.id = id;
        this.userId = userId;
        this.queueStatus = queueStatus;
        this.issuedAt = issuedAt;
        this.lastActiveId = lastActiveId;
    }
}
