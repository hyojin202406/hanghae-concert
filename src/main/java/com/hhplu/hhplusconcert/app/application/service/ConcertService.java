package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.concert.Schedule;
import com.hhplu.hhplusconcert.app.domain.concert.Seat;
import com.hhplu.hhplusconcert.app.infrastructure.concert.ScheduleJpaRepository;
import com.hhplu.hhplusconcert.app.infrastructure.concert.SeatJpaRepository;
import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    @Autowired
    ScheduleJpaRepository scheduleRepository;

    @Autowired
    SeatJpaRepository seatRepository;

    public List<Schedule> schedule(Long concertId) {
        List<Schedule> schedules = scheduleRepository.findByConcertId(concertId);
        Schedule.validateSchedules(schedules);
        return schedules;
    }

    public List<Seat> seats(Long scheduleId) {
        List<Seat> seats = seatRepository.findByScheduleId(scheduleId).orElseThrow(() -> new IllegalArgumentException("좌석을 찾을 수 없습니다."));
        return seats;
    }

    public List<Seat> seats(Long concertId, Long scheduleId) {
        List<Seat> seats = seatRepository.findByScheduleIdAndStatus(scheduleId, SeatStatus.AVAILABLE).orElseThrow(() -> new IllegalArgumentException("좌석을 찾을 수 없습니다."));
        return seats;
    }
}
