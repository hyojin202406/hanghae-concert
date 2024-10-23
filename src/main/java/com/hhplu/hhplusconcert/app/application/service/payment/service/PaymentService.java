package com.hhplu.hhplusconcert.app.application.service.payment.service;

import com.hhplu.hhplusconcert.app.common.error.ErrorCode;
import com.hhplu.hhplusconcert.app.common.exception.BaseException;
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

        // 결제 정보 생성
        Payment payment = Payment.builder()
                .reservationId(reservation.getId())
                .amount(BigDecimal.valueOf(sumPoint))
                .paymentStatus(PaymentStatus.PENDING) // 결제 준비 상태
                .paymentAt(LocalDateTime.now())
                .build();

        // Payment(PENDING 상태) 저장
        Payment savedPayment = paymentRepository.savePayment(payment);

        reservation.changePaymentId(savedPayment.getId());
    }

    public Payment getPayment(Long paymentId) {
        return paymentRepository.getPayment(paymentId)
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_NOT_FOUND));
    }
}
