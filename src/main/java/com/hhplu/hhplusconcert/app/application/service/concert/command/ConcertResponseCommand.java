package com.hhplu.hhplusconcert.app.application.service.concert.command;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class ConcertResponseCommand {
    @NotNull
    public Long concertId;

    @NotNull
    public List<Schedule> schedules;

    public ConcertResponseCommand(Long concertId, List<Schedule> schedules) {
        this.concertId = concertId;
        this.schedules = schedules;
    }
}
