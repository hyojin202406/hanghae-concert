package com.hhplu.hhplusconcert.app.domain.reservation.event.listener;

import com.hhplu.hhplusconcert.app.domain.reservation.event.dto.ReservationSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ReservationEventListener {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void reservationSuccessHandler(ReservationSuccessEvent event) {
        log.info("Reservation success event received, {}", event);
    }
}