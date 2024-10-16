package com.hhplu.hhplusconcert.app.domain.concert;

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

    @Column(nullable = false, name = "reservation_id")
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

    // 상태 변경 메서드
    public void changeStatus(SeatStatus newStatus) {
        this.status = newStatus;
    }

    public void changeReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
}
