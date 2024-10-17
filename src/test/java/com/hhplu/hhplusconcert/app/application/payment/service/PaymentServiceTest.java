package com.hhplu.hhplusconcert.app.application.payment.service;

import com.hhplu.hhplusconcert.app.domain.payment.PaymentStatus;
import com.hhplu.hhplusconcert.app.domain.payment.entity.Payment;
import com.hhplu.hhplusconcert.app.domain.payment.repository.PaymentRepository;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationStatus;
import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;


    @Test
    void 임시_예약_결제_생성_성공() {
        // given
        long reservationId = 1L;
        long sumPoint = 100L;

        // Reservation 객체 생성
        Reservation reservation = Reservation.builder()
                .id(reservationId) // ID 설정
                .userId(1L) // 사용자 ID 설정 (필요시)
                .paymentId(null) // 결제 정보는 초기 상태
                .reservedAt(LocalDateTime.now())
                .reservationStatus(ReservationStatus.TEMPORARY_RESERVED)
                .build();

        // Payment 객체 생성
        Payment payment = Payment.builder()
                .reservationId(reservationId)
                .amount(BigDecimal.valueOf(sumPoint))
                .paymentStatus(PaymentStatus.PENDING)
                .paymentAt(LocalDateTime.now())
                .build();

        // when
        when(paymentRepository.savePayment(any(Payment.class))).thenReturn(payment);

        paymentService.createPendingPayment(reservation, sumPoint);

        // then
        assertEquals(payment.getReservationId(), reservation.getId());
        assertEquals(PaymentStatus.PENDING, payment.getPaymentStatus());
        verify(paymentRepository).savePayment(any(Payment.class));
    }

    @Test
    void 경제_조회_성공() {
        // given
        Long paymentId = 1L;
        Payment payment = Payment.builder()
                .id(paymentId)
                .reservationId(1L)
                .amount(BigDecimal.valueOf(100L))
                .paymentStatus(PaymentStatus.PENDING)
                .paymentAt(LocalDateTime.now())
                .build();

        // when
        when(paymentRepository.getPayment(paymentId)).thenReturn(payment);

        Payment foundPayment = paymentService.getPayment(paymentId);

        // then
        assertEquals(paymentId, foundPayment.getId());
        assertEquals(PaymentStatus.PENDING, foundPayment.getPaymentStatus());
        verify(paymentRepository).getPayment(paymentId);
    }
}