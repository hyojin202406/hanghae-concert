package com.hhplu.hhplusconcert.interfaces.api.reservation.res;

import com.hhplu.hhplusconcert.interfaces.api.concert.dto.Seat;
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
    private List<Seat> seats;
    private int totalPrice;
    private String reservationStatus;
}