package com.hhplu.hhplusconcert.app.application.service.point.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RechargeCommand {

    @NotNull
    private Long userId;
    @NotNull
    private BigDecimal pointAmount;

    public RechargeCommand(Long userId, long pointAmount) {
        this.userId = userId;
        this.pointAmount = BigDecimal.valueOf(pointAmount);
    }
}
