package com.hhplu.hhplusconcert.app.domain.payment.repository;

import com.hhplu.hhplusconcert.app.domain.payment.entity.PaymentHistory;

import java.util.List;
import java.util.Optional;

public interface PaymentHistoryRepository {
    PaymentHistory savePaymentHistory(PaymentHistory payment);

    PaymentHistory getPaymentHistory(Long paymentId);

    Optional<List<PaymentHistory>> getPaymentHistoryByUserId(Long userId);
}
