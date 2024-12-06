package com.hhplu.hhplusconcert.app.application.service.concert.service;

import com.hhplu.hhplusconcert.common.error.ErrorCode;
import com.hhplu.hhplusconcert.common.exception.BaseException;
import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public List<Seat> getAllSeatsByScheduleId(Long scheduleId) {
        return seatRepository.getAllSeatsByScheduleId(scheduleId)
                .orElseThrow(() -> new BaseException(ErrorCode.SEAT_NOT_FOUND));
    }

    public List<Seat> getAvailableSeatsByScheduleId(Long scheduleId) {
        return seatRepository.getAvailableSeatsByScheduleId(scheduleId, SeatStatus.AVAILABLE)
                .orElseThrow(() -> new BaseException(ErrorCode.SEAT_NOT_FOUND));
    }

    public List<Seat> updateSeatStatus(Long reservationId, Long scheduleId, Long[] seatIds) {
        List<Seat> seats = seatRepository.findSeatsByScheduleId(seatIds, scheduleId);
        if (seats.size() != seatIds.length) {
            throw new BaseException(ErrorCode.SEAT_NOT_FOUND);
        }
        seats.forEach(seat -> {
            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                throw new BaseException(ErrorCode.SEAT_NOT_AVAILABLE);
            }
            seat.changeStatus(SeatStatus.TEMPORARILY_RESERVED);
            seat.changeReservationId(reservationId);
            seat.extendExpiration();
        });

        return seats;
    }

    public void reserveSeats(Long reservationId) {
        seatRepository.findSeatsByReservationId(reservationId)
                .orElseThrow(() -> new BaseException(ErrorCode.SEAT_NOT_AVAILABLE))
                .forEach(seat -> seat.changeStatus(SeatStatus.RESERVED));
    }

    public long calculateTotalPoint(List<Seat> seats) {
        return seats.stream()
                .mapToLong(Seat::getSeatPrice)
                .sum();
    }
}
