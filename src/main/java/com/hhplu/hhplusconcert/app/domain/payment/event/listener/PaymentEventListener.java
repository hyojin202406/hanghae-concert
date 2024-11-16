package com.hhplu.hhplusconcert.app.domain.payment.event.listener;

import com.hhplu.hhplusconcert.app.domain.payment.event.dto.PaymentSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class PaymentEventListener {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void paymentSuccessHandler(PaymentSuccessEvent event) {
        log.info("Payment success event received, {}", event);
    }
}