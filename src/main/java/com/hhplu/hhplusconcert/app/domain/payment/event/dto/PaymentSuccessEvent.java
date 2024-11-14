package com.hhplu.hhplusconcert.app.domain.payment.event.dto;

import lombok.Getter;

@Getter
public class PaymentSuccessEvent {
    private final Long orderKey;
    private final Long paymentKey;

    public PaymentSuccessEvent(Long orderKey, Long paymentKey) {
        this.orderKey = orderKey;
        this.paymentKey = paymentKey;
    }
}