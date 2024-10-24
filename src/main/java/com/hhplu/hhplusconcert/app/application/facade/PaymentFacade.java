package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.service.concert.service.SeatService;
import com.hhplu.hhplusconcert.app.application.service.payment.command.GetPaymentsHistoryResponseCommand;
import com.hhplu.hhplusconcert.app.application.service.payment.command.PaymentRequestCommand;
import com.hhplu.hhplusconcert.app.application.service.payment.command.PaymentResponseCommand;
import com.hhplu.hhplusconcert.app.application.service.payment.service.PaymentHistoryService;
import com.hhplu.hhplusconcert.app.application.service.payment.service.PaymentService;
import com.hhplu.hhplusconcert.app.application.service.point.service.PointService;
import com.hhplu.hhplusconcert.app.application.service.reservation.service.ReservationService;
import com.hhplu.hhplusconcert.app.application.service.user.service.UserService;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.service.WaitingQueueService;
import com.hhplu.hhplusconcert.app.domain.payment.PaymentStatus;
import com.hhplu.hhplusconcert.app.domain.payment.entity.Payment;
import com.hhplu.hhplusconcert.app.domain.payment.entity.PaymentHistory;
import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import com.hhplu.hhplusconcert.app.domain.waitingqueue.WaitingQueueStatus;
import com.hhplu.hhplusconcert.app.domain.waitingqueue.entity.WaitingQueue;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
        User user = userService.user(paymentRequestCommand.getUserId());
        Payment payment = paymentService.getPayment(paymentRequestCommand.getPaymentId());
        pointService.subtractUserPoints(user.getId(), payment.getAmount());
        seatService.reserveSeats(payment.getReservationId());
        payment.changePaymentStatus(PaymentStatus.COMPLETED);
        WaitingQueue token = waitingQueueService.getToken(paymentRequestCommand.getQueueToken());
        token.changeWaitingQueueStatus(WaitingQueueStatus.EXPIRED);
        paymentHistoryService.createPaymentHistory(user.getId(), payment.getId(), payment.getPaymentStatus(), payment.getAmount(), payment.getPaymentAt());
        return new PaymentResponseCommand(payment.getId(), payment.getAmount(), payment.getPaymentStatus());
    }

    public GetPaymentsHistoryResponseCommand getPayments(Long userId) {
        User user = userService.user(userId);
        List<PaymentHistory> paymentsHistory = paymentHistoryService.getPaymentsHistory(user.getId());
        return new GetPaymentsHistoryResponseCommand(user.getId(), paymentsHistory);
    }
}
