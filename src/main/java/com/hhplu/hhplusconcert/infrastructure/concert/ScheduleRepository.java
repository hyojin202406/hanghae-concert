package com.hhplu.hhplusconcert.infrastructure.concert;

import com.hhplu.hhplusconcert.domain.concert.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByConcertId(Long concertId);
}
