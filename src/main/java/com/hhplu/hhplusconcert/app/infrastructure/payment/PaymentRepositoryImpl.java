package com.hhplu.hhplusconcert.app.infrastructure.payment;

import com.hhplu.hhplusconcert.app.domain.payment.entity.Payment;
import com.hhplu.hhplusconcert.app.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment savePayment(Payment payment) {
        return paymentJpaRepository.save(payment);
    }

    @Override
    public Payment getPayment(Long paymentId) {
        return paymentJpaRepository.findByPaymentId(paymentId);
    }
}
