package com.hhplu.hhplusconcert.app.application.service.concert.service;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import com.hhplu.hhplusconcert.app.domain.concert.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> schedule(Long concertId) {
        List<Schedule> schedules = scheduleRepository.existsSchedule(concertId);
        Schedule.existSchedules(schedules);
        return schedules;
    }

    @Cacheable(value = "concertSchedules")
    public List<Schedule> redisCacheableSchedule(Long concertId) {
        List<Schedule> schedules = scheduleRepository.existsSchedule(concertId);
        Schedule.existSchedules(schedules);
        return schedules;
    }

    public List<Schedule> validateScheduleExists(Long scheduleId) {
        List<Schedule> schedules = scheduleRepository.existsSchedule(scheduleId);
        Schedule.existSchedules(schedules);
        return schedules;
    }
}
