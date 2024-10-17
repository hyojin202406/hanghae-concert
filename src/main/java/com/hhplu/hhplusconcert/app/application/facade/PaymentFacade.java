package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.concert.service.SeatService;
import com.hhplu.hhplusconcert.app.application.payment.command.GetPaymentsHistoryResponseCommand;
import com.hhplu.hhplusconcert.app.application.payment.command.PaymentRequestCommand;
import com.hhplu.hhplusconcert.app.application.payment.command.PaymentResponseCommand;
import com.hhplu.hhplusconcert.app.application.payment.service.PaymentHistoryService;
import com.hhplu.hhplusconcert.app.application.payment.service.PaymentService;
import com.hhplu.hhplusconcert.app.application.point.service.PointService;
import com.hhplu.hhplusconcert.app.application.reservation.service.ReservationService;
import com.hhplu.hhplusconcert.app.application.user.service.UserService;
import com.hhplu.hhplusconcert.app.application.waitingqueue.service.WaitingQueueService;
import com.hhplu.hhplusconcert.app.domain.payment.PaymentStatus;
import com.hhplu.hhplusconcert.app.domain.payment.entity.Payment;
import com.hhplu.hhplusconcert.app.domain.payment.entity.PaymentHistory;
import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import com.hhplu.hhplusconcert.app.domain.watingqueue.WaitingQueueStatus;
import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentFacade {

    private final UserService userService;
    private final PointService pointService;
    private final PaymentService paymentService;
    private final PaymentHistoryService paymentHistoryService;
    private final SeatService seatService;
    private final ReservationService reservationService;
    private final WaitingQueueService waitingQueueService;

    @Transactional
    public PaymentResponseCommand pay(PaymentRequestCommand paymentRequestCommand) {
        // 유저, 결제 유효성 검사
        User user = userService.user(paymentRequestCommand.getUserId());
        Payment payment = paymentService.getPayment(paymentRequestCommand.getPaymentId());
        // 포인트 차감
        pointService.subtractUserPoints(user.getId(), payment.getAmount());
        // 좌석 예약
        seatService.reserveSeats(payment.getReservationId());
        // 결제 완료 상태 변경
        payment.changePaymentStatus(PaymentStatus.COMPLETED);
        // 대기열 토큰 만료 처리
        WaitingQueue token = waitingQueueService.getToken(paymentRequestCommand.getQueueToken());
        token.changeWaitingQueueStatus(WaitingQueueStatus.EXPIRED);
        // 결제 내역 저장
        paymentHistoryService.createPaymentHistory(user.getId(), payment.getId(), payment.getPaymentStatus(), payment.getAmount(), payment.getPaymentAt());

        // 결제 응답 생성
        return new PaymentResponseCommand(payment.getId(), payment.getAmount(), payment.getPaymentStatus());
    }

    public GetPaymentsHistoryResponseCommand getPayments(Long userId) {
        // 유저, 결제 유효성 검사
        User user = userService.user(userId);
        List<PaymentHistory> paymentsHistory = paymentHistoryService.getPaymentsHistory(user.getId());
        return new GetPaymentsHistoryResponseCommand(user.getId(), paymentsHistory);
    }
}
