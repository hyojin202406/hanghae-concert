package com.hhplu.hhplusconcert.domain.concert;

import com.hhplu.hhplusconcert.infrastructure.concert.SeatStatus;
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
}
