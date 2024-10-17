package com.hhplu.hhplusconcert.app.interfaces.api.concert.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SeatItem {
    private Long seatId;
    private Long seatNumber;
    private String seatStatus;
    private Long seatPrice;
}

