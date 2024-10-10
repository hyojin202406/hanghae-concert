package com.hhplu.hhplusconcert.interfaces.api.reservation.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReservationRequest {
    private Long userId;
    private Long concertId;
    private Long scheduleId;
    private Long[] seatIdsArr;
}
