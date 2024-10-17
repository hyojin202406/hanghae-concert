package com.hhplu.hhplusconcert.app.application.payment.command;

import com.hhplu.hhplusconcert.app.domain.payment.entity.PaymentHistory;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class GetPaymentsHistoryResponseCommand {
    @NotNull
    private Long userId;

    private List<PaymentHistory> payments;

    public GetPaymentsHistoryResponseCommand(Long userId, List<PaymentHistory> payments) {
        this.userId = userId;
        this.payments = payments;
    }
}
