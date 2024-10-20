package com.hhplu.hhplusconcert.app.domain.payment.repository;

import com.hhplu.hhplusconcert.app.domain.payment.entity.Payment;

import java.util.Optional;

public interface PaymentRepository {
    Payment savePayment(Payment payment);

    Optional<Payment> getPayment(Long paymentId);

    Payment existsPayment(Long paymentId);
}
