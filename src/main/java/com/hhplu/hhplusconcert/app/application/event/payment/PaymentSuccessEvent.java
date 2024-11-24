package com.hhplu.hhplusconcert.app.application.event.payment;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PaymentSuccessEvent {
    private final String eventKey;          // 고유 식별자
    private final Long orderKey;            // 주문 키
    private final Long paymentKey;          // 결제 키
    private final String version;           // 메시지 버전
    private final LocalDateTime createdAt;  // 생성 시간

    public PaymentSuccessEvent(Long orderKey, Long paymentKey) {
        this.eventKey = UUID.randomUUID().toString();
        this.orderKey = orderKey;
        this.paymentKey = paymentKey;
        this.version = "1.0";
        this.createdAt = LocalDateTime.now();
    }
}