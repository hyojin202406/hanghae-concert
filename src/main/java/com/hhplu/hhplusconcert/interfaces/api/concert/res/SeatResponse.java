package com.hhplu.hhplusconcert.interfaces.api.concert.res;

import com.hhplu.hhplusconcert.interfaces.api.concert.dto.SeatValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SeatResponse {
    private Long concertId;
    private Long scheduleId;
    private List<SeatValue> allSeats;
    private List<SeatValue> availableSeats;
}