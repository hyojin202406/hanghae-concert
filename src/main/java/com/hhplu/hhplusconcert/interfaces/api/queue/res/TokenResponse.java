package com.hhplu.hhplusconcert.interfaces.api.queue.res;


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

}
