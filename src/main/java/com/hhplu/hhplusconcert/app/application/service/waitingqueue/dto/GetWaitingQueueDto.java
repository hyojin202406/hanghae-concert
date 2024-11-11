package com.hhplu.hhplusconcert.app.application.service.waitingqueue.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GetWaitingQueueDto {

    @NotNull
    private Integer waitingQueuePosition;

    public GetWaitingQueueDto(int waitingQueuePosition) {
        this.waitingQueuePosition = waitingQueuePosition;
    }
}
