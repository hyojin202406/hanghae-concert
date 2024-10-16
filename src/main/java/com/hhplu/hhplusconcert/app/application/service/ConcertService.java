package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import com.hhplu.hhplusconcert.app.infrastructure.concert.ScheduleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    @Autowired
    ScheduleJpaRepository scheduleRepository;

    public List<Schedule> schedule(Long concertId) {
        List<Schedule> schedules = scheduleRepository.findByConcertId(concertId);
        Schedule.validateSchedules(schedules);
        return schedules;
    }

}
