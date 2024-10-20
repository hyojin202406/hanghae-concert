package com.hhplu.hhplusconcert.app.application.service.point.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class GetPointCommand {
    @NotNull
    private BigDecimal currentPointAmount;

    public GetPointCommand(BigDecimal pointAmount) {
        this.currentPointAmount = pointAmount;
    }
}
