package com.hhplu.hhplusconcert.app.application.event.payment;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PaymentSuccessEvent {
    private final String eventKey;
    private final Long orderKey;
    private final Long paymentKey;

    public PaymentSuccessEvent(Long orderKey, Long paymentKey) {
        this.eventKey = UUID.randomUUID().toString(); // 동일한 UUID 생성
        this.orderKey = orderKey;
        this.paymentKey = paymentKey;
    }
}