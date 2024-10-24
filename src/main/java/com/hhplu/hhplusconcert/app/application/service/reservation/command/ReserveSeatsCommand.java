package com.hhplu.hhplusconcert.app.application.service.reservation.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReserveSeatsCommand {
    @NotNull
    private Long userId;
    @NotNull
    private Long concertId;
    @NotNull
    private Long scheduleId;
    @NotNull
    private Long[] seatIds;

    public ReserveSeatsCommand(Long userId, Long concertId, Long scheduleId, @NotNull Long[] seatIds) {
        this.userId = userId;
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.seatIds = seatIds;
    }
}
