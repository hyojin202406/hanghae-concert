package com.hhplu.hhplusconcert.app.domain.concert.repository;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    List<Schedule> getSchedulesByConcertId(Long concertId);

    Schedule getSchedulesByScheduleId(Long scheduleId);
}
