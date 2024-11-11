package com.hhplu.hhplusconcert.app.application.service.payment.dto;

import com.hhplu.hhplusconcert.app.domain.payment.entity.PaymentHistory;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class GetPaymentsHistoryResponseDto {
    @NotNull
    private Long userId;

    private List<PaymentHistory> payments;

    public GetPaymentsHistoryResponseDto(Long userId, List<PaymentHistory> payments) {
        this.userId = userId;
        this.payments = payments;
    }
}
