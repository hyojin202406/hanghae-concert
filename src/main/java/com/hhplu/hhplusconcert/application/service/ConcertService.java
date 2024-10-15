package com.hhplu.hhplusconcert.application.service;

import com.hhplu.hhplusconcert.domain.concert.Schedule;
import com.hhplu.hhplusconcert.domain.concert.Seat;
import com.hhplu.hhplusconcert.infrastructure.concert.ScheduleRepository;
import com.hhplu.hhplusconcert.infrastructure.concert.SeatRepository;
import com.hhplu.hhplusconcert.infrastructure.concert.SeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    SeatRepository seatRepository;

    public List<Schedule> schedule(Long concertId) {
        List<Schedule> schedules = scheduleRepository.findByConcertId(concertId);
        Schedule.validateSchedules(schedules);
        return schedules;
    }

    public List<Seat> seats(Long scheduleId) {
        List<Seat> seats = seatRepository.findByScheduleId(scheduleId).orElseThrow(() -> new IllegalArgumentException("Seat not found"));
        return seats;
    }

    public List<Seat> seats(Long concertId, Long scheduleId) {
        List<Seat> seats = seatRepository.findByScheduleIdAndStatus(scheduleId, SeatStatus.AVAILABLE).orElseThrow(() -> new IllegalArgumentException("Seat not found"));
        return seats;
    }
}
