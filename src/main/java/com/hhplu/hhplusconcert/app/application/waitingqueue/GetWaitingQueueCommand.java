package com.hhplu.hhplusconcert.app.application.waitingqueue;

import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GetWaitingQueueCommand {
    @NotNull
    private WaitingQueue waitingQueue;

    @NotNull
    private Long lastActiveId;

    public GetWaitingQueueCommand(WaitingQueue waitingQueue, Long lastActiveId) {
        this.waitingQueue = waitingQueue;
        this.lastActiveId = lastActiveId;
    }
}
