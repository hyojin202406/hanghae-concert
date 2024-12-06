package com.hhplu.hhplusconcert.app.application.service.payment;

import com.hhplu.hhplusconcert.common.error.ErrorCode;
import com.hhplu.hhplusconcert.common.exception.BaseException;
import com.hhplu.hhplusconcert.app.domain.payment.PaymentStatus;
import com.hhplu.hhplusconcert.app.domain.payment.entity.Payment;
import com.hhplu.hhplusconcert.app.domain.payment.repository.PaymentRepository;
import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void createPendingPayment(Reservation reservation, long sumPoint) {
        Payment savedPayment = paymentRepository.savePayment(Payment.from(reservation, sumPoint));
        reservation.changePaymentId(savedPayment.getId());
    }

    public Payment getPayment(Long paymentId) {
        return paymentRepository.getPayment(paymentId)
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_NOT_FOUND));
    }
}
