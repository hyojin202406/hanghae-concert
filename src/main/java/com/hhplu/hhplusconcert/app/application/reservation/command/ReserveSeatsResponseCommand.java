package com.hhplu.hhplusconcert.app.application.reservation.command;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Concert;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;
import com.hhplu.hhplusconcert.app.interfaces.api.concert.dto.SeatValue;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReserveSeatsResponseCommand {
    private Long reservationId;
    private Long concertId;
    private String concertName;
    private LocalDateTime concertAt;
    private List<SeatValue> seats;
    private long totalPrice;
    private String reservationStatus;

    public ReserveSeatsResponseCommand(Reservation reservation, Concert concert, List<Seat> seats, long sumPoint) {
        this.reservationId = reservation.getId();
        this.concertId = concert.getId();
        this.concertName = concert.getConsertName();
        this.concertAt = concert.getCreatedAt();
        this.seats = seats.stream()
                .map(seat -> new SeatValue(
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
