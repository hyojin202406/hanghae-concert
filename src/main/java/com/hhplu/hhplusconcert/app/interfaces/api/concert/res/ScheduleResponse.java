package com.hhplu.hhplusconcert.app.interfaces.api.concert.res;

import com.hhplu.hhplusconcert.app.application.service.concert.command.ConcertResponseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ScheduleResponse {
    private Long concertId;
    private List<ScheduleItem> events;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ScheduleItem {
        private Long scheduleId;
        private LocalDateTime scheduleStartedAt;
    }

    public static ScheduleResponse from(ConcertResponseCommand command) {
        List<ScheduleItem> list = command.getSchedules().stream().map(schedule -> new ScheduleItem(
                schedule.getId(),
                schedule.getScheduleStaredtAt()
        )).toList();
        return ScheduleResponse.builder()
                .concertId(command.getConcertId())
                .events(list)
                .build();
    }
}
