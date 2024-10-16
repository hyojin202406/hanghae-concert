package com.hhplu.hhplusconcert.app.interfaces.api.point.res;

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
