package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.repository.SeatRepository;
import com.hhplu.hhplusconcert.app.domain.payment.PaymentStatus;
import com.hhplu.hhplusconcert.app.domain.payment.entity.Payment;
import com.hhplu.hhplusconcert.app.domain.payment.entity.PaymentHistory;
import com.hhplu.hhplusconcert.app.domain.payment.repository.PaymentHistoryRepository;
import com.hhplu.hhplusconcert.app.domain.payment.repository.PaymentRepository;
import com.hhplu.hhplusconcert.app.domain.watingqueue.WaitingQueueStatus;
import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;
import com.hhplu.hhplusconcert.app.domain.watingqueue.repository.WaitingQueueRepository;
import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import com.hhplu.hhplusconcert.app.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final SeatRepository seatRepository;
    private final WaitingQueueRepository waitingQueueRepository;

    /**
     * 결제
     *
     * @param userId
     * @param queueToken
     * @param paymentId
     * @param totalAmount
     * @param seats
     * @return
     */
    @Transactional
    public Payment payment(Long userId, String queueToken, Long paymentId, BigDecimal totalAmount, List<Seat> seats) {
        // 유저 정보 조회
        User user = userRepository.getUser(userId);
        if (user.getPointAmount().compareTo(totalAmount) < 0) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        // 결제 조회
        Payment payment = paymentRepository.getPayment(paymentId);
        try {
            // 포인트 차감
            user.subtractPointAmount(totalAmount);

            // 좌석 예약
            for (Seat seat : seats) {
                seat.changeReservationId(payment.getReservationId());
                seat.changeStatus(SeatStatus.RESERVED);
            }

            // 결제 완료 상태 변경
            payment.changePaymentStatus(PaymentStatus.COMPLETED);

            // 대기열 토큰 만료 처리
            WaitingQueue queue = waitingQueueRepository.getToken(queueToken);
            queue.changeWaitingQueueStatus(WaitingQueueStatus.EXPIRED);

            // 결제 내역 저장
            PaymentHistory paymentHistory = PaymentHistory.builder()
                    .paymentId(payment.getId())
                    .paymentStatus(PaymentStatus.COMPLETED)
                    .amount(totalAmount) // 총 결제 금액
                    .paymentAt(LocalDateTime.now()) // 결제 시각
                    .build();

            paymentHistoryRepository.savePaymentHistory(paymentHistory);
        } catch (Exception e) {
            // 결제 내역 저장 실패
            PaymentHistory failedPaymentHistory = PaymentHistory.builder()
                    .paymentId(payment.getId())
                    .paymentStatus(PaymentStatus.FAILED)
                    .amount(totalAmount)
                    .paymentAt(LocalDateTime.now())
                    .build();

            paymentHistoryRepository.savePaymentHistory(failedPaymentHistory);

            throw new RuntimeException("결제 처리 중 오류가 발생했습니다.", e);
        }

        return payment;
    }
}
