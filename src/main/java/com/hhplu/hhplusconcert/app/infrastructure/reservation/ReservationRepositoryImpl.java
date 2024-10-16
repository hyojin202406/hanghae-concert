package com.hhplu.hhplusconcert.app.infrastructure.reservation;

import com.hhplu.hhplusconcert.app.domain.reservation.Reservation;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Reservation saveReservation(Reservation reservation) {
        return reservationJpaRepository.save(reservation);
    }

}
