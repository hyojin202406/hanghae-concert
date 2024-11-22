package com.hhplu.hhplusconcert.app.domain.payment;

import com.hhplu.hhplusconcert.app.application.event.payment.PaymentSuccessEvent;

public interface PaymentMessageProducer {
    void send(String topic, String eventKey, PaymentSuccessEvent Message);
}
