package com.hhplu.hhplusconcert.app.application.service.point.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class GetPointDto {
    @NotNull
    private BigDecimal currentPointAmount;

    public GetPointDto(BigDecimal pointAmount) {
        this.currentPointAmount = pointAmount;
    }
}
