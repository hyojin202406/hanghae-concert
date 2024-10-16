package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.repository.SeatRepository;
import com.hhplu.hhplusconcert.app.domain.payment.PaymentStatus;
import com.hhplu.hhplusconcert.app.domain.payment.entity.Payment;
import com.hhplu.hhplusconcert.app.domain.payment.repository.PaymentRepository;
import com.hhplu.hhplusconcert.app.domain.watingqueue.WaitingQueueStatus;
import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;
import com.hhplu.hhplusconcert.app.domain.watingqueue.repository.WaitingQueueRepository;
import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import com.hhplu.hhplusconcert.app.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    SeatRepository seatRepository;

    @Mock
    WaitingQueueRepository queueRepository;

    @InjectMocks
    PaymentService paymentService;


    @Test
    void 결제성공시() {
        // Given
        Long userId = 1L;
        String queueToken = "testQueueToken";
        Long paymentId = 1L;
        Long reservationId = 1L;
        BigDecimal totalAmount = BigDecimal.valueOf(50000L);
        List<Seat> seats = Arrays.asList(
                Seat.builder()
                        .id(1L).scheduleId(1L).reservationId(reservationId).seatPrice(50000L).status(SeatStatus.AVAILABLE).build(),
                Seat.builder()
                        .id(2L).scheduleId(1L).reservationId(reservationId).seatPrice(50000L).status(SeatStatus.AVAILABLE).build()
        );

        User user = User.builder()
                .id(userId)
                .name("TestUser")
                .pointAmount(BigDecimal.valueOf(100000L))
                .build();

        Payment payment = Payment.builder()
                .id(paymentId)
                .reservationId(reservationId)
                .amount(totalAmount)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        WaitingQueue queue = WaitingQueue.builder()
                .queueToken(queueToken)
                .queueStatus(WaitingQueueStatus.ACTIVE)
                .build();

        // Mocking
        when(userRepository.getUser(userId)).thenReturn(user);
        when(paymentRepository.getPayment(paymentId)).thenReturn(payment);
        when(queueRepository.getToken(queueToken)).thenReturn(queue);

        // When
        Payment result = paymentService.payment(userId, queueToken, paymentId, totalAmount, seats);

        // Then
        assertNotNull(result);
        assertEquals(PaymentStatus.COMPLETED, result.getPaymentStatus());
        assertEquals(totalAmount, result.getAmount());

        // 포인트 차감 검증
        assertEquals(BigDecimal.valueOf(50000L), user.getPointAmount());

        // 좌석 상태 검증
        for (Seat seat : seats) {
            assertEquals(SeatStatus.RESERVED, seat.getStatus());
            assertEquals(reservationId, seat.getReservationId());
        }

        // 대기열 만료 검증
        assertEquals(WaitingQueueStatus.EXPIRED, queue.getQueueStatus());

        // Verification
        verify(userRepository).getUser(userId);
        verify(paymentRepository).getPayment(paymentId);
    }

    @Test
    void 잔액부족시_예외발생() {
        // Given
        Long userId = 1L;
        String queueToken = "testQueueToken";
        Long paymentId = 1L;
        BigDecimal totalAmount = BigDecimal.valueOf(50000L);

        User user = User.builder()
                .id(userId)
                .name("TestUser")
                .pointAmount(BigDecimal.valueOf(30000L)) // 잔액 부족
                .build();

        // Mocking
        when(userRepository.getUser(userId)).thenReturn(user);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.payment(userId, queueToken, paymentId, totalAmount, null);
        });

        assertEquals("잔액이 부족합니다.", exception.getMessage());
        verify(userRepository).getUser(userId);
        verify(paymentRepository, never()).getPayment(anyLong());
    }
}