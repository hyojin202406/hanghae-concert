package com.hhplu.hhplusconcert.interfaces.api.concert.res;

import com.hhplu.hhplusconcert.interfaces.api.concert.dto.Seat;
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
    private List<Seat> allSeats;
    private List<Seat> availableSeats;
}