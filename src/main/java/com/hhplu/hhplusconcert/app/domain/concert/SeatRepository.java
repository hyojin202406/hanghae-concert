package com.hhplu.hhplusconcert.app.domain.concert;

import java.util.List;

public interface SeatRepository {
    List<Seat> findSeatsByScheduleId(Long[] seatIds, Long scheduleId);

    void saveSeats(List<Seat> seats);
}
