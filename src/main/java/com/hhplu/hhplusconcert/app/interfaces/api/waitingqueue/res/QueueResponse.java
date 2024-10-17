package com.hhplu.hhplusconcert.app.interfaces.api.waitingqueue.res;

import com.hhplu.hhplusconcert.app.domain.watingqueue.WaitingQueueStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class QueueResponse {
    private Long userId;
    private String queueToken;
    private Long queuePosition;
    private Long lastActivedQueuePosition;
    private WaitingQueueStatus queueStatus;
    private LocalDateTime issuedAt;
}
