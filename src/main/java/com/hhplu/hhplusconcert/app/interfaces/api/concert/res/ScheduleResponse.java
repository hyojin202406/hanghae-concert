package com.hhplu.hhplusconcert.app.interfaces.api.concert.res;

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
    private List<EventResponse> events; // events 필드 추가

    @Getter
    @Builder
    @AllArgsConstructor
    public static class EventResponse {
        private Long scheduleId;
        private LocalDateTime concertAt;
    }
}
