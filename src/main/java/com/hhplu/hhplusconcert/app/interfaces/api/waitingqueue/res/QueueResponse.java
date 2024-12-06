package com.hhplu.hhplusconcert.app.interfaces.api.waitingqueue.res;

import com.hhplu.hhplusconcert.app.application.service.waitingqueue.dto.GetWaitingQueueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QueueResponse {
    private Integer queuePosition;

    public static QueueResponse from(GetWaitingQueueDto command) {
        return QueueResponse.builder()
                .queuePosition(command.getWaitingQueuePosition())
                .build();
    }
}
