package com.hhplu.hhplusconcert.app.application.service.reservation.dto;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Concert;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;
import com.hhplu.hhplusconcert.app.interfaces.api.concert.dto.SeatItem;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReserveSeatsResponseDto {
    private Long reservationId;
    private Long concertId;
    private String concertName;
    private LocalDateTime concertAt;
    private List<SeatItem> seats;
    private long totalPrice;
    private String reservationStatus;

    public ReserveSeatsResponseDto(Reservation reservation, Concert concert, List<Seat> seats, long sumPoint) {
        this.reservationId = reservation.getId();
        this.concertId = concert.getId();
        this.concertName = concert.getConsertName();
        this.concertAt = concert.getCreatedAt();
        this.seats = seats.stream()
                .map(seat -> new SeatItem(
                        seat.getId(),
                        seat.getSeatNumber(),
                        seat.getStatus().toString(),
                        seat.getSeatPrice()
                ))
                .toList();
        this.totalPrice = sumPoint;
        this.reservationStatus = reservation.getReservationStatus().toString();
    }
}
