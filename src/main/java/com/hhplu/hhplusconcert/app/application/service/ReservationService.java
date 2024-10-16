package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.concert.ConcertRepository;
import com.hhplu.hhplusconcert.app.domain.concert.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.SeatRepository;
import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.reservation.Reservation;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationRepository;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ConcertRepository concertRepository;
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    public Reservation reserveSeats(Long userId, Long concertId, Long scheduleId, Long[] seatIds) {

        // 콘서트 존재 여부 확인
        concertRepository.existsConcert(concertId);

        // 좌석 유효성 확인
        List<Seat> seats = seatRepository.findSeatsByScheduleId(seatIds, scheduleId);
        if (seats.size() != seatIds.length) {
            throw new IllegalArgumentException("잘못된 좌석 정보가 포함되어 있습니다.");
        }

        // 모든 좌석이 예약 가능한 상태인지 확인
        for (Seat seat : seats) {
            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                throw new IllegalStateException("예약할 수 없는 좌석이 포함되어 있습니다.");
            }
        }

        // 예약 정보 생성 및 저장
        Reservation reservation = Reservation.builder()
                .userId(userId)
                .paymentId(null) // 결제 정보는 별도 처리로 가정
                .reservedAt(LocalDateTime.now())
                .reservationStatus(ReservationStatus.TEMPORARY_RESERVED)
                .build();
        reservationRepository.saveReservation(reservation);

        // 좌석 상태 업데이트 및 예약 ID 설정
        seats.forEach(seat -> {
            seat.changeStatus(SeatStatus.RESERVED);
            seat.changeReservationId(reservation.getId());
        });
        seatRepository.saveSeats(seats);

        return reservation;
    }
}
