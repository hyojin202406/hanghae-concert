package com.hhplu.hhplusconcert.app.interfaces.api.payment.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long amount;
    private String paymentStatus;
}
