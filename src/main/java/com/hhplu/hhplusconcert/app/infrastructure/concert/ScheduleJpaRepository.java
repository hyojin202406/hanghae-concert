package com.hhplu.hhplusconcert.app.infrastructure.concert;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleJpaRepository extends JpaRepository<Schedule, Long> {
    Optional<List<Schedule>> findByConcertId(Long concertId);

    Optional<Schedule> findById(Long scheduleId);
}
