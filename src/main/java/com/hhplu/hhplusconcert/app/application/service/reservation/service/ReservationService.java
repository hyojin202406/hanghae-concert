package com.hhplu.hhplusconcert.app.application.service.reservation.service;

import com.hhplu.hhplusconcert.app.domain.reservation.ReservationStatus;
import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;
import com.hhplu.hhplusconcert.app.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation createReservation(Long userId) {
        // 예약 정보 생성 및 저장
        Reservation reservation = Reservation.builder()
                .userId(userId)
                .paymentId(null) // 결제 정보는 별도 처리
                .reservedAt(LocalDateTime.now())
                .reservationStatus(ReservationStatus.TEMPORARY_RESERVED)
                .build();
        reservationRepository.saveReservation(reservation);

        return reservation;
    }

}
