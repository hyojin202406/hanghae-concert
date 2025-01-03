package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.service.concert.dto.ConcertResponseDto;
import com.hhplu.hhplusconcert.app.application.service.concert.dto.ConcertSeatsResponseDto;
import com.hhplu.hhplusconcert.app.application.service.concert.service.ConcertService;
import com.hhplu.hhplusconcert.app.application.service.concert.service.ScheduleService;
import com.hhplu.hhplusconcert.app.application.service.concert.service.SeatService;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Concert;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConcertFacade {
    private final ConcertService concertService;
    private final ScheduleService scheduleService;
    private final SeatService seatService;

    public ConcertResponseDto getConcertSchedules(Long concertId) {
        Concert concert = concertService.validateConcertExists(concertId);
        List<Schedule> schedules = scheduleService.schedule(concert.getId());
        return new ConcertResponseDto(concert.getId(), schedules);
    }

    public ConcertSeatsResponseDto getConcertSeats(Long concertId, Long scheduleId) {
        Concert concert = concertService.validateConcertExists(concertId);
        Schedule schedule = scheduleService.validateScheduleExists(scheduleId);
        List<Seat> allSeats = seatService.getAllSeatsByScheduleId(scheduleId);
        List<Seat> availableSeats = seatService.getAvailableSeatsByScheduleId(scheduleId);
        return new ConcertSeatsResponseDto(concert.getId(), schedule.getId(), allSeats, availableSeats);
    }
}
