package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.concert.repository.ConcertRepository;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.repository.SeatRepository;
import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.payment.PaymentStatus;
import com.hhplu.hhplusconcert.app.domain.payment.entity.Payment;
import com.hhplu.hhplusconcert.app.domain.payment.repository.PaymentRepository;
import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;
import com.hhplu.hhplusconcert.app.domain.reservation.repository.ReservationRepository;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationStatus;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ConcertRepository concertRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final SeatRepository seatRepository;

    /**
     * 좌석 예약
     * @param userId
     * @param concertId
     * @param scheduleId
     * @param seatIds
     * @return
     */
    @Transactional
    public Reservation reserveSeats(Long userId, Long concertId, Long scheduleId, Long[] seatIds) {

        Reservation reservation = new Reservation();

        try {
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
            reservation = Reservation.builder()
                    .userId(userId)
                    .paymentId(null) // 결제 정보는 별도 처리로 가정
                    .reservedAt(LocalDateTime.now())
                    .reservationStatus(ReservationStatus.TEMPORARY_RESERVED)
                    .build();
            reservationRepository.saveReservation(reservation);

            // 좌석 상태 업데이트 및 예약 ID 설정
            Long reservationId = reservation.getId();
            seats.forEach(seat -> {
                seat.changeStatus(SeatStatus.TEMPORARILY_RESERVED);
                seat.changeReservationId(reservationId);
                seat.extendExpiration();
            });

            // 결제 정보 생성 및 저장 (PENDING 상태)
            // 총 결제 금액 계산
            long sumPoint = seats.stream()
                    .mapToLong(Seat::getSeatPrice)
                    .sum();
            Payment payment = Payment.builder()
                    .reservationId(reservationId)
                    .amount(BigDecimal.valueOf(sumPoint)) // 총 결제 금액 계산
                    .paymentStatus(PaymentStatus.PENDING) // 결제 준비 상태
                    .paymentAt(LocalDateTime.now())
                    .build();

            // Payment(PENDING 상태) 저장
            Payment savedPayment = paymentRepository.savePayment(payment);

            reservation.changePaymentId(savedPayment.getId());
        } catch (OptimisticLockException e) {
            throw new IllegalStateException("좌석 예약에 실패했습니다. 다시 시도해 주세요.");
        }


        return reservation;
    }
}
