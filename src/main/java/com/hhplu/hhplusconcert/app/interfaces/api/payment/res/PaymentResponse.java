package com.hhplu.hhplusconcert.app.interfaces.api.payment.res;

import com.hhplu.hhplusconcert.app.domain.payment.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long paymentId;
    private BigDecimal amount;
    private String paymentStatus;

    public PaymentResponse(Long paymentId, BigDecimal amount, PaymentStatus paymentStatus) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentStatus = paymentStatus.toString();
    }
}
