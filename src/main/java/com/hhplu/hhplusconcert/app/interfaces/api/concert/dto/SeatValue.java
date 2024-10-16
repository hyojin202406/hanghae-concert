package com.hhplu.hhplusconcert.app.interfaces.api.concert.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SeatValue {
    private Long seatId;
    private int seatNumber;
    private String seatStatus;
    private int seatPrice;
}
