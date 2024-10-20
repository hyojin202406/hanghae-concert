package com.hhplu.hhplusconcert.app.application.service.concert.service;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Concert;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import com.hhplu.hhplusconcert.app.domain.concert.repository.ConcertRepository;
import com.hhplu.hhplusconcert.app.domain.concert.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> schedule(Long concertId) {
        concertRepository.existsConcert(concertId);
        List<Schedule> schedules = scheduleRepository.existsSchedule(concertId);
        Schedule.validateSchedules(schedules);
        return schedules;
    }

    public Concert validateConcertExists(Long concertId) {
        return concertRepository.existsConcert(concertId);
    }
}
