package com.hhplu.hhplusconcert.app.infrastructure.reservation;

import com.hhplu.hhplusconcert.app.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
}
