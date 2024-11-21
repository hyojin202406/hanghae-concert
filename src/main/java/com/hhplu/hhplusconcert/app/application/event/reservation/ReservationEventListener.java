package com.hhplu.hhplusconcert.app.application.event.reservation;

import com.hhplu.hhplusconcert.app.application.event.payment.PaymentSuccessEvent;
import com.hhplu.hhplusconcert.app.application.service.outbox.OutBoxService;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventListener {

    private final ReservationMessageProducer reservationMessageProducer;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveReservationOutBox(ReservationSuccessEvent event) {
        // 예약정보 저장
        log.info("saveReservationOutBox : {}", event);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservation(ReservationSuccessEvent event) {
        // 카프카 발행
        log.info("handleReservation : {}", event);
        reservationMessageProducer.send("reservation-topic", event);
    }
}