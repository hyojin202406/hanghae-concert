package com.hhplu.hhplusconcert.app.infrastructure.payment;

import com.hhplu.hhplusconcert.app.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
    Payment findByPaymentId(Long paymentId);
}
