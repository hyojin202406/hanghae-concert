package com.hhplu.hhplusconcert.interfaces.api.reservation.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    private Long userId;
    private Long concertId;
    private Long scheduleId;
    private Long[] seatIdsArr;
}
