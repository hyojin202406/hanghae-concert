package com.hhplu.hhplusconcert.app.infrastructure.concert;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

    Optional<List<Seat>> findByScheduleId(Long concertId);

    Optional<List<Seat>> findByScheduleIdAndStatus(Long scheduleId, SeatStatus seatStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Seat> findAllByIdInAndScheduleId(Long[] seatIds, Long scheduleId);

    List<Seat> findAllByStatusAndExpiredAtBefore(SeatStatus seatStatus, LocalDateTime now);

    Optional<List<Seat>> findByReservationId(Long reservationId);
}
