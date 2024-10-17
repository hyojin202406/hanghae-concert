package com.hhplu.hhplusconcert.app.application.payment.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentRequestCommand {

    @NotNull
    private Long userId;

    @NotNull
    private Long paymentId;

    @NotNull
    private String queueToken;

    public PaymentRequestCommand(Long userId, Long paymentId, String queueToken) {
        this.userId = userId;
        this.paymentId = paymentId;
        this.queueToken = queueToken;
    }
}
