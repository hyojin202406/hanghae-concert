package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    @Autowired
    SeatRepository seatRepository;

    public List<Seat> getAllSeatsByScheduleId(Long scheduleId) {
        return seatRepository.getAllSeatsByScheduleId(scheduleId);
    }

    public List<Seat> getAvailableSeatsByScheduleId(Long concertId, Long scheduleId) {
        return seatRepository.getAvailableSeatsByScheduleId(scheduleId, SeatStatus.AVAILABLE);
    }

}
