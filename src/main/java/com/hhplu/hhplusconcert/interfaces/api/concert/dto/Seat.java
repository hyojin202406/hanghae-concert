package com.hhplu.hhplusconcert.interfaces.api.concert.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Seat {
    private Long seatId;
    private int seatNumber;
    private String seatStatus;
    private int seatPrice;
}

