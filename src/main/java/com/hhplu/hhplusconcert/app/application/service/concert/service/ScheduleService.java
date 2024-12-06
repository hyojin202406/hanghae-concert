package com.hhplu.hhplusconcert.app.application.service.concert.service;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import com.hhplu.hhplusconcert.app.domain.concert.repository.ScheduleRepository;
import com.hhplu.hhplusconcert.common.error.ErrorCode;
import com.hhplu.hhplusconcert.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Cacheable(value = "concertSchedules")
    public List<Schedule> schedule(Long concertId) {
        return scheduleRepository.getSchedulesByConcertId(concertId)
                .orElseGet(ArrayList::new);
    }

    public Schedule validateScheduleExists(Long scheduleId) {
        return scheduleRepository.getSchedulesByScheduleId(scheduleId)
                .orElseThrow(() -> new BaseException(ErrorCode.CONCERT_SCHEDULE_NOT_FOUND));
    }
}
