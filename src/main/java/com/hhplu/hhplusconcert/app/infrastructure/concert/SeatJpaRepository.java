package com.hhplu.hhplusconcert.app.infrastructure.concert;

import com.hhplu.hhplusconcert.app.domain.concert.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

    Optional<List<Seat>> findByScheduleId(Long concertId);

    Optional<List<Seat>> findByScheduleIdAndStatus(Long scheduleId, SeatStatus seatStatus);

    List<Seat> findAllByIdInAndScheduleId(Long[] seatIds, Long scheduleId);

}
