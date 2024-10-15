package com.hhplu.hhplusconcert.application.service;

import com.hhplu.hhplusconcert.domain.concert.Schedule;
import com.hhplu.hhplusconcert.infrastructure.concert.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public List<Schedule> schedule(Long concertId) {
        List<Schedule> schedules = scheduleRepository.findByConcertId(concertId);
        Schedule.validateSchedules(schedules);
        return schedules;
    }

}
