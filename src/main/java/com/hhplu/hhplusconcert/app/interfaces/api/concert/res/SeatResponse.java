package com.hhplu.hhplusconcert.app.interfaces.api.concert.res;

import com.hhplu.hhplusconcert.app.application.service.concert.command.ConcertSeatsResponseCommand;
import com.hhplu.hhplusconcert.app.interfaces.api.concert.dto.SeatItem;
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
    private List<SeatItem> allSeats;
    private List<SeatItem> availableSeats;

    public static SeatResponse from(ConcertSeatsResponseCommand command) {

        List<SeatItem> allSeats = command.getAllSeats().stream().map(seat -> new SeatItem(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getStatus().toString(),
                seat.getSeatPrice()
        )).toList();

        List<SeatItem> availableSeats = command.getAvailableSeats().stream().map(seat -> new SeatItem(
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