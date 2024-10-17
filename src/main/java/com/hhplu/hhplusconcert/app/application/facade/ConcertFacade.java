package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.concert.command.ConcertResponseCommand;
import com.hhplu.hhplusconcert.app.application.concert.command.ConcertSeatsResponseCommand;
import com.hhplu.hhplusconcert.app.application.concert.service.ScheduleService;
import com.hhplu.hhplusconcert.app.application.service.ConcertService;
import com.hhplu.hhplusconcert.app.application.service.SeatService;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Concert;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertFacade {
    private final ConcertService concertService;
    private final ScheduleService scheduleService;
    private final SeatService seatService;

    public ConcertResponseCommand getConcertSchedules(Long concertId) {
        Concert concert = concertService.validateConcertExists(concertId);
        List<Schedule> schedules = concertService.schedule(concert.getId());
        return new ConcertResponseCommand(concert.getId(), schedules);
    }

    public ConcertSeatsResponseCommand getConcertSeats(Long concertId, Long scheduleId) {
        Concert concert = concertService.validateConcertExists(concertId);
        List<Schedule> schedule = scheduleService.validateScheduleExists(scheduleId);
        List<Seat> allSeats = seatService.getAllSeatsByScheduleId(scheduleId);
        List<Seat> availableSeats = seatService.getAvailableSeatsByScheduleId(scheduleId);
        return new ConcertSeatsResponseCommand(concert.getId(), schedule.get(0).getId(), allSeats, availableSeats);
    }
}
