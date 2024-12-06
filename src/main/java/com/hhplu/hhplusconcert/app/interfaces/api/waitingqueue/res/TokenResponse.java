package com.hhplu.hhplusconcert.app.interfaces.api.waitingqueue.res;


import com.hhplu.hhplusconcert.app.application.service.waitingqueue.dto.CreateWaitingQueueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TokenResponse {
    private Long userId;
    private String queueToken;
    private LocalDateTime issuedAt;

    public static TokenResponse from(CreateWaitingQueueDto command) {
        return TokenResponse.builder()
                .userId(command.getUserId())
                .queueToken(command.getQueueToken())
                .issuedAt(command.getIssuedAt())
                .build();
    }
}
