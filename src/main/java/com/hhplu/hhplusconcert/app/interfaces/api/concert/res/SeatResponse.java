package com.hhplu.hhplusconcert.app.interfaces.api.concert.res;

import com.hhplu.hhplusconcert.app.application.concert.command.ConcertResponseCommand;
import com.hhplu.hhplusconcert.app.application.concert.command.ConcertSeatsResponseCommand;
import com.hhplu.hhplusconcert.app.interfaces.api.concert.dto.SeatValue;
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

    public static SeatResponse from(ConcertSeatsResponseCommand command) {

        List<SeatValue> allSeats = command.getAllSeats().stream().map(seat -> new SeatValue(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getStatus().toString(),
                seat.getSeatPrice()
        )).toList();

        List<SeatValue> availableSeats = command.getAvailableSeats().stream().map(seat -> new SeatValue(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getStatus().toString(),
                seat.getSeatPrice()
        )).toList();

        return SeatResponse.builder()
                .concertId(command.getConcertId())
                .scheduleId(command.getScheduleId())
                .allSeats(allSeats)
                .availableSeats(availableSeats)
                .build();
    }
}