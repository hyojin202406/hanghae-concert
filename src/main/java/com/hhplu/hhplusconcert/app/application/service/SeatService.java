package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.infrastructure.concert.SeatJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    @Autowired
    SeatJpaRepository seatRepository;

    public List<Seat> seats(Long scheduleId) {
        return seatRepository.findByScheduleId(scheduleId).orElseThrow(() -> new IllegalArgumentException("좌석을 찾을 수 없습니다."));
    }

    public List<Seat> seats(Long concertId, Long scheduleId) {
        return seatRepository.findByScheduleIdAndStatus(scheduleId, SeatStatus.AVAILABLE).orElseThrow(() -> new IllegalArgumentException("좌석을 찾을 수 없습니다."));
    }

}
