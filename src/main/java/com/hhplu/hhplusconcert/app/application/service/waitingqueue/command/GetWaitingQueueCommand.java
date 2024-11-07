package com.hhplu.hhplusconcert.app.application.service.waitingqueue.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GetWaitingQueueCommand {

    @NotNull
    private Integer waitingQueuePosition;

    public GetWaitingQueueCommand(int waitingQueuePosition) {
        this.waitingQueuePosition = waitingQueuePosition;
    }
}
