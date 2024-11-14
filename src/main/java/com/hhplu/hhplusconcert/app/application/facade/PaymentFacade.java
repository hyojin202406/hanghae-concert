package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.event.PaymentEventPublisher;
import com.hhplu.hhplusconcert.app.application.service.concert.service.SeatService;
import com.hhplu.hhplusconcert.app.application.service.payment.dto.GetPaymentsHistoryResponseDto;
import com.hhplu.hhplusconcert.app.application.service.payment.dto.PaymentRequestDto;
import com.hhplu.hhplusconcert.app.application.service.payment.dto.PaymentResponseDto;
import com.hhplu.hhplusconcert.app.application.service.payment.service.PaymentHistoryService;
import com.hhplu.hhplusconcert.app.application.service.payment.service.PaymentService;
import com.hhplu.hhplusconcert.app.application.service.point.service.PointService;
import com.hhplu.hhplusconcert.app.application.service.user.service.UserService;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.service.WaitingQueueRedisService;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.service.WaitingQueueService;
import com.hhplu.hhplusconcert.app.domain.payment.entity.Payment;
import com.hhplu.hhplusconcert.app.domain.payment.entity.PaymentHistory;
import com.hhplu.hhplusconcert.app.domain.payment.event.dto.PaymentSuccessEvent;
import com.hhplu.hhplusconcert.app.domain.user.entity.User;
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
    private final WaitingQueueService waitingQueueService;
    private final WaitingQueueRedisService waitingQueueRedisService;
    private final PaymentEventPublisher eventPublisher;

    @Transactional
    public PaymentResponseDto pay(PaymentRequestDto paymentRequestDto) {
        User user = userService.user(paymentRequestDto.getUserId());

        Payment payment = paymentService.getPayment(paymentRequestDto.getPaymentId());
        pointService.subtractUserPoints(user.getId(), payment.getAmount());
        seatService.reserveSeats(payment.getReservationId());
        payment.completedStaus();

        waitingQueueRedisService.removeActiveToken(paymentRequestDto.getQueueToken());

        paymentHistoryService.createPaymentHistory(user.getId(), payment.getId(), payment.getPaymentStatus(), payment.getAmount(), payment.getPaymentAt());

        eventPublisher.success(new PaymentSuccessEvent(payment.getReservationId(), payment.getId()));

        return new PaymentResponseDto(payment.getId(), payment.getAmount(), payment.getPaymentStatus());
    }

    public GetPaymentsHistoryResponseDto getPayments(Long userId) {
        User user = userService.user(userId);
        List<PaymentHistory> paymentsHistory = paymentHistoryService.getPaymentsHistory(user.getId());
        return new GetPaymentsHistoryResponseDto(user.getId(), paymentsHistory);
    }

}
