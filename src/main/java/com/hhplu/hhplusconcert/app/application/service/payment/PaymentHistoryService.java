package com.hhplu.hhplusconcert.app.application.service.payment;

import com.hhplu.hhplusconcert.common.error.ErrorCode;
import com.hhplu.hhplusconcert.common.exception.BaseException;
import com.hhplu.hhplusconcert.app.domain.payment.PaymentStatus;
import com.hhplu.hhplusconcert.app.domain.payment.entity.PaymentHistory;
import com.hhplu.hhplusconcert.app.domain.payment.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    public PaymentHistory createPaymentHistory(Long userId, Long paymentId, PaymentStatus paymentStatus, BigDecimal amount, LocalDateTime paymentAt) {
        return paymentHistoryRepository.savePaymentHistory(PaymentHistory.from(userId, paymentId, paymentStatus, amount, paymentAt));
    }

    public List<PaymentHistory> getPaymentsHistory(Long userId) {
        return paymentHistoryRepository.getPaymentHistoryByUserId(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_HISTORY_NOT_FOUND));
    }
}
