package com.hhplu.hhplusconcert.app.interfaces.api.point.res;

import com.hhplu.hhplusconcert.app.application.service.point.dto.GetPointDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
public class PointResponse {
    private Long userId;
    private BigDecimal currentPointAmount;

    public static PointResponse from(Long userId, GetPointDto command) {
        return PointResponse.builder()
                .userId(userId)
                .currentPointAmount(command.getCurrentPointAmount())
                .build();
    }
}
