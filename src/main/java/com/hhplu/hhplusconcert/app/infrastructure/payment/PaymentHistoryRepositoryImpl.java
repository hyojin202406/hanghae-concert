package com.hhplu.hhplusconcert.app.infrastructure.payment;

import com.hhplu.hhplusconcert.app.domain.payment.entity.PaymentHistory;
import com.hhplu.hhplusconcert.app.domain.payment.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentHistoryRepositoryImpl implements PaymentHistoryRepository {

    private final PaymentHistoryJpaRepository paymentHistoryJpaRepository;

    @Override
    public PaymentHistory savePaymentHistory(PaymentHistory payment) {
        return paymentHistoryJpaRepository.save(payment);
    }

    @Override
    public PaymentHistory getPaymentHistory(Long paymentId) {
        return paymentHistoryJpaRepository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("결재내역이 존재하지 않습니다."));
    }
}
