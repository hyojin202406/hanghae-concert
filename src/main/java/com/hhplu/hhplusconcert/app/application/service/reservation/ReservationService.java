package com.hhplu.hhplusconcert.app.application.service.reservation;

import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;
import com.hhplu.hhplusconcert.app.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation createReservation(Long userId) {
        Reservation reservation = Reservation.from(userId);
        reservationRepository.saveReservation(reservation);
        return reservation;
    }

}
