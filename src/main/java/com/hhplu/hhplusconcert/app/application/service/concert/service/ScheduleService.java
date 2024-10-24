package com.hhplu.hhplusconcert.app.application.service.concert.service;

import com.hhplu.hhplusconcert.common.error.ErrorCode;
import com.hhplu.hhplusconcert.common.exception.BaseException;
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
            throw new BaseException(ErrorCode.CONCERT_SCHEDULE_NOT_FOUND);
        }
        return schedules;
    }
}
