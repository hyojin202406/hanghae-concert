package com.hhplu.hhplusconcert.app.interfaces.api.reservation.req;

import com.hhplu.hhplusconcert.app.application.service.reservation.dto.ReserveSeatsDto;
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

    public ReserveSeatsDto toReserveSeatsCommand() {
        return new ReserveSeatsDto(userId, concertId, scheduleId, seatIdsArr);
    }
}
