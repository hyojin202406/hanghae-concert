package com.hhplu.hhplusconcert.app.infrastructure.concert;

import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    @Override
    public List<Seat> getAllSeatsByScheduleId(Long scheduleId) {
        return seatJpaRepository.findByScheduleId(scheduleId).orElseThrow(() -> new IllegalArgumentException("좌석을 찾을 수 없습니다."));
    }

    @Override
    public List<Seat> getAvailableSeatsByScheduleId(Long scheduleId, SeatStatus seatStatus) {
        return seatJpaRepository.findByScheduleIdAndStatus(scheduleId, SeatStatus.AVAILABLE).orElseThrow(() -> new IllegalArgumentException("좌석을 찾을 수 없습니다."));
    }

    @Override
    public List<Seat> findAllByStatusAndExpiredAtBefore(SeatStatus seatStatus, LocalDateTime now) {
        return seatJpaRepository.findAllByStatusAndExpiredAtBefore(seatStatus, now);
    }
}
