package com.hhplu.hhplusconcert.app.application.concert.command;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class ConcertSeatsResponseCommand {
    @NotNull
    private Long concertId;

    @NotNull
    private Long scheduleId;

    @NotNull
    private List<Seat>  allSeats;

    @NotNull
    private List<Seat>  availableSeats;

    public ConcertSeatsResponseCommand(Long concertId, Long scheduleId, List<Seat> allSeats, List<Seat> availableSeats) {
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.allSeats = allSeats;
        this.availableSeats = availableSeats;
    }
}
