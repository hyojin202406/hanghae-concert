package com.hhplu.hhplusconcert.interfaces.api.payment.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PointResponse {
    private Long userId;
    private Long currentPointAmount;
}
