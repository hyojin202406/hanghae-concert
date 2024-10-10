package com.hhplu.hhplusconcert.interfaces.api.reservation.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PointRequest {
    private Long pointAmount;
}
