package com.hhplu.hhplusconcert.app.domain.concert.repository;

import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeatRepository {
    List<Seat> findSeatsByScheduleId(Long[] seatIds, Long scheduleId);

    void saveSeats(List<Seat> seats);

    Optional<List<Seat>> getAllSeatsByScheduleId(Long scheduleId);

    Optional<List<Seat>> getAvailableSeatsByScheduleId(Long scheduleId, SeatStatus seatStatus);

    List<Seat> findAllByStatusAndExpiredAtBefore(SeatStatus seatStatus, LocalDateTime now);

    Optional<List<Seat>> findSeatsByReservationId(Long reservationId);
}
