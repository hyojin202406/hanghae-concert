package com.hhplu.hhplusconcert.app.domain.concert.repository;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Optional<List<Schedule>> getSchedulesByConcertId(Long concertId);

    Optional<Schedule> getSchedulesByScheduleId(Long scheduleId);
}
