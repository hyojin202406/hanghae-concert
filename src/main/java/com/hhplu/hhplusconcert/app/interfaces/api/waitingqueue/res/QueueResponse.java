package com.hhplu.hhplusconcert.app.interfaces.api.waitingqueue.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QueueResponse {
    private Integer queuePosition;
}
