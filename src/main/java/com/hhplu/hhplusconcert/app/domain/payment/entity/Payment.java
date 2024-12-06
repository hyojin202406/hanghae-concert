package com.hhplu.hhplusconcert.app.domain.payment.entity;

import com.hhplu.hhplusconcert.app.domain.payment.PaymentStatus;
import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "reservation_id")
    private Long reservationId;

    @Column(nullable = false, precision = 10, scale = 2, name = "amount")
    private BigDecimal amount;

    @Column(nullable = false, name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false, name = "payment_at")
    private LocalDateTime paymentAt;

    public static Payment from(Reservation reservation, long sumPoint) {
        return Payment.builder()
                .reservationId(reservation.getId())
                .amount(BigDecimal.valueOf(sumPoint))
                .paymentStatus(PaymentStatus.PENDING)
                .paymentAt(LocalDateTime.now())
                .build();
    }


    public void completedStaus() {
        this.paymentStatus = PaymentStatus.COMPLETED;
    }
}
