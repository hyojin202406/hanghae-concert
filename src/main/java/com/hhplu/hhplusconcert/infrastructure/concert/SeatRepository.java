package com.hhplu.hhplusconcert.infrastructure.concert;

import com.hhplu.hhplusconcert.domain.concert.Seat;
import com.hhplu.hhplusconcert.domain.concert.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    Optional<List<Seat>> findByScheduleId(Long concertId);

    Optional<List<Seat>> findByScheduleIdAndStatus(Long scheduleId, SeatStatus seatStatus);

    List<Seat> findAllByIdInAndScheduleId(Long[] seatIds, Long scheduleId);

}
