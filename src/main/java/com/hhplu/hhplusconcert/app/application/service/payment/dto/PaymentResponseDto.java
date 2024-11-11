package com.hhplu.hhplusconcert.app.application.service.payment.dto;

import com.hhplu.hhplusconcert.app.domain.payment.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PaymentResponseDto {
    @NotNull
    private Long paymentId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private PaymentStatus paymentStatus;

    public PaymentResponseDto(Long paymentId, BigDecimal amount, PaymentStatus paymentStatus) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }
}
