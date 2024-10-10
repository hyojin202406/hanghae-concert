package com.hhplu.hhplusconcert.interfaces.api.reservation.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PointResponse {
    private Long userId;
    private Long currentAmount;
}
