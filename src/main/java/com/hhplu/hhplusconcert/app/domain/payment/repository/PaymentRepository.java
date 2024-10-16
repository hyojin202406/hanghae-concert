package com.hhplu.hhplusconcert.app.domain.payment.repository;

import com.hhplu.hhplusconcert.app.domain.payment.entity.Payment;

public interface PaymentRepository {
    Payment savePayment(Payment payment);

    Payment getPayment(Long paymentId);
}
