package com.hhplu.hhplusconcert.interfaces.api.reservation.res;

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
