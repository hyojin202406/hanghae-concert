package com.hhplu.hhplusconcert.app.domain.payment.repository;

import com.hhplu.hhplusconcert.app.domain.payment.entity.PaymentHistory;

public interface PaymentHistoryRepository {
    PaymentHistory savePaymentHistory(PaymentHistory payment);

    PaymentHistory getPaymentHistory(Long paymentId);
}
