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

    public List<Seat> getAvailableSeatsByScheduleId(Long scheduleId) {
        return seatRepository.getAvailableSeatsByScheduleId(scheduleId, SeatStatus.AVAILABLE);
    }

    public List<Seat> updateSeatStatus(Long reservationId, Long scheduleId, Long[] seatIds) {
        // 좌석 유효성 확인
        List<Seat> seats = seatRepository.findSeatsByScheduleId(seatIds, scheduleId);
        if (seats.size() != seatIds.length) {
            throw new IllegalArgumentException("잘못된 좌석 정보가 포함되어 있습니다.");
        }

        // 좌석 상태 업데이트
        seats.forEach(seat -> {
            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                throw new IllegalStateException("예약할 수 없는 좌석이 포함되어 있습니다.");
            }
            seat.changeStatus(SeatStatus.TEMPORARILY_RESERVED);
            seat.changeReservationId(reservationId);
            seat.extendExpiration();
        });

        return seats;
    }
}
