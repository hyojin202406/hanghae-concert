package com.hhplu.hhplusconcert.app.interfaces.api.point.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointRequest {
    private Long pointAmount;
}
