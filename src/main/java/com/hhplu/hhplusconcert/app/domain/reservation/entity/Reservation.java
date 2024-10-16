package com.hhplu.hhplusconcert.app.domain.reservation.entity;

import com.hhplu.hhplusconcert.app.domain.reservation.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "payment_id")
    Long paymentId;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus;
}
