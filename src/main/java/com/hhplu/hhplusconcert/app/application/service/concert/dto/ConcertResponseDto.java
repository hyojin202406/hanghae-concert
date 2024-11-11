package com.hhplu.hhplusconcert.app.application.service.concert.dto;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class ConcertResponseDto {
    @NotNull
    public Long concertId;

    @NotNull
    public List<Schedule> schedules;

    public ConcertResponseDto(Long concertId, List<Schedule> schedules) {
        this.concertId = concertId;
        this.schedules = schedules;
    }
}
