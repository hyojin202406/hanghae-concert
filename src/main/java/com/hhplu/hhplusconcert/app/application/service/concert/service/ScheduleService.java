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

    @Cacheable(value = "concertSchedules")
    public List<Schedule> schedule(Long concertId) {
        return scheduleRepository.getSchedulesByConcertId(concertId);
    }

    public Schedule validateScheduleExists(Long scheduleId) {
        return scheduleRepository.getSchedulesByScheduleId(scheduleId);
    }
}
