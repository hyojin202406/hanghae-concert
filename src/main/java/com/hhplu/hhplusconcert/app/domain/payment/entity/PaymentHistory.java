package com.hhplu.hhplusconcert.app.domain.payment.entity;

import com.hhplu.hhplusconcert.app.domain.payment.PaymentStatus;
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
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "payment_id")
    private Long paymentId;

    @Column(nullable = false, name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false, precision = 10, scale = 2, name = "amount")
    private BigDecimal amount;

    @Column(nullable = false, name = "payment_at")
    private LocalDateTime paymentAt;

}
