package com.hhplu.hhplusconcert.app.interfaces.api.reservation.res;

import com.hhplu.hhplusconcert.app.interfaces.api.concert.dto.SeatValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ReservationResponse {
    private Long reservationId;
    private Long concertId;
    private String concertName;
    private LocalDateTime concertAt;
    private List<SeatValue> seats;
    private int totalPrice;
    private String reservationStatus;
}