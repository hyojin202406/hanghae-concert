package com.hhplu.hhplusconcert.app.infrastructure.payment;

import com.hhplu.hhplusconcert.app.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findById(Long paymentId);
}
