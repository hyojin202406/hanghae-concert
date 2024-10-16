package com.hhplu.hhplusconcert.app.domain.reservation.repository;

import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;

public interface ReservationRepository {
    Reservation saveReservation(Reservation reservation);
}
