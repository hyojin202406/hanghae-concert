package com.hhplu.hhplusconcert.app.application.service.point.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RechargeDto {

    @NotNull
    private Long userId;
    @NotNull
    private BigDecimal pointAmount;

    public RechargeDto(Long userId, long pointAmount) {
        this.userId = userId;
        this.pointAmount = BigDecimal.valueOf(pointAmount);
    }
}
