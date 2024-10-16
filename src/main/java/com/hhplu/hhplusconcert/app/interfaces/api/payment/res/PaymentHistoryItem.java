package com.hhplu.hhplusconcert.app.interfaces.api.payment.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PaymentHistoryItem {
    private Long paymentId;
    private BigDecimal amount; // BigDecimal로 수정
    private String paymentStatus;
    private LocalDateTime paymentAt;
}
