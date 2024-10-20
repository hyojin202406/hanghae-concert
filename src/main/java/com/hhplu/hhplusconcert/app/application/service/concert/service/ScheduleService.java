package com.hhplu.hhplusconcert.app.application.service.concert.service;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import com.hhplu.hhplusconcert.app.domain.concert.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> validateScheduleExists(Long scheduleId) {
        List<Schedule> schedules = scheduleRepository.existsSchedule(scheduleId);
        if (schedules.isEmpty()) {
            throw new IllegalArgumentException("콘서트 일정이 존재하지 않습니다.");
        }
        return schedules;
    }
}
