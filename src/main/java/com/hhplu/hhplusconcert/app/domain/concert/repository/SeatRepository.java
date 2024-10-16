package com.hhplu.hhplusconcert.app.domain.concert.repository;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;

import java.util.List;

public interface SeatRepository {
    List<Seat> findSeatsByScheduleId(Long[] seatIds, Long scheduleId);

    void saveSeats(List<Seat> seats);
}
