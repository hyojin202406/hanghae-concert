package com.hhplu.hhplusconcert.app.application.waitingqueue;

import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateWaitingQueueCommand {
    @NotNull
    private WaitingQueue waitingQueue;

    public CreateWaitingQueueCommand(WaitingQueue waitingQueue) {
        this.waitingQueue = waitingQueue;
    }
}
