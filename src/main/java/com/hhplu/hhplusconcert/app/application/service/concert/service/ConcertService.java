package com.hhplu.hhplusconcert.app.application.service.concert.service;

import com.hhplu.hhplusconcert.app.common.error.ErrorCode;
import com.hhplu.hhplusconcert.app.common.exception.BaseException;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Concert;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import com.hhplu.hhplusconcert.app.domain.concert.repository.ConcertRepository;
import com.hhplu.hhplusconcert.app.domain.concert.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> schedule(Long concertId) {
        concertRepository.existsConcert(concertId)
                .orElseThrow(() -> new BaseException(ErrorCode.CONCERT_NOT_FOUND));
        List<Schedule> schedules = scheduleRepository.existsSchedule(concertId);
        Schedule.validateSchedules(schedules);
        return schedules;
    }

    public Concert validateConcertExists(Long concertId) {
        return concertRepository.existsConcert(concertId)
                .orElseThrow(() -> new BaseException(ErrorCode.CONCERT_NOT_FOUND));
    }
}
