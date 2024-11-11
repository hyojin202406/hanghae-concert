package com.hhplu.hhplusconcert.app.application.service.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long paymentId;

    @NotNull
    private String queueToken;

    public PaymentRequestDto(Long userId, Long paymentId, String queueToken) {
        this.userId = userId;
        this.paymentId = paymentId;
        this.queueToken = queueToken;
    }
}
