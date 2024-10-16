package com.hhplu.hhplusconcert.infrastructure.reservation;

import com.hhplu.hhplusconcert.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
