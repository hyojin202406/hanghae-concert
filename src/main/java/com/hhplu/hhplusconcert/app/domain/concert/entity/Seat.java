package com.hhplu.hhplusconcert.app.domain.concert.entity;

import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "schedule_id")
    private Long scheduleId;

    @Column(nullable = true, name = "reservation_id")
    private Long reservationId;

    @Column(nullable = false, name = "seat_number")
    private Long seatNumber;

    @Column(nullable = false, name = "seat_price")
    private Long seatPrice;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "expired_at")
    private LocalDateTime expiredAt;

    @Version
    private Long version;

    public void changeStatus(SeatStatus newStatus) {
        this.status = newStatus;
    }

    public void changeReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public void extendExpiration() {
        this.expiredAt = LocalDateTime.now().plusMinutes(5);
    }
}
