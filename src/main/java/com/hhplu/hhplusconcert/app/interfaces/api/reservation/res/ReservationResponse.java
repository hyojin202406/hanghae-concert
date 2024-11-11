package com.hhplu.hhplusconcert.app.interfaces.api.reservation.res;

import com.hhplu.hhplusconcert.app.application.service.reservation.dto.ReserveSeatsResponseDto;
import com.hhplu.hhplusconcert.app.interfaces.api.concert.dto.SeatItem;
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
    private List<SeatItem> seats;
    private Long totalPrice;
    private String reservationStatus;

    public static ReservationResponse from(ReserveSeatsResponseDto command) {
        return ReservationResponse.builder()
                .reservationId(command.getReservationId())
                .concertId(command.getConcertId())
                .concertName(command.getConcertName())
                .concertAt(command.getConcertAt())
                .seats(command.getSeats())
                .totalPrice(command.getTotalPrice())
                .reservationStatus(command.getReservationStatus())
                .build();
    }
}