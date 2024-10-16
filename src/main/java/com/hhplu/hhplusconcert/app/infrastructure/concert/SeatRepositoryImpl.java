package com.hhplu.hhplusconcert.app.infrastructure.concert;

import com.hhplu.hhplusconcert.app.domain.concert.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Seat> findSeatsByScheduleId(Long[] seatIds, Long scheduleId) {
        return seatJpaRepository.findAllByIdInAndScheduleId(seatIds, scheduleId);
    }

    @Override
    public void saveSeats(List<Seat> seats) {
        seatJpaRepository.saveAll(seats);
    }
}
