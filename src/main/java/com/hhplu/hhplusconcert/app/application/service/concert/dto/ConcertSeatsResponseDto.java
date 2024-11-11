package com.hhplu.hhplusconcert.app.application.service.concert.dto;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class ConcertSeatsResponseDto {
    @NotNull
    private Long concertId;

    @NotNull
    private Long scheduleId;

    @NotNull
    private List<Seat>  allSeats;

    @NotNull
    private List<Seat>  availableSeats;

    public ConcertSeatsResponseDto(Long concertId, Long scheduleId, List<Seat> allSeats, List<Seat> availableSeats) {
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.allSeats = allSeats;
        this.availableSeats = availableSeats;
    }
}
