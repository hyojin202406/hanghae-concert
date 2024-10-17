package com.hhplu.hhplusconcert.app.domain.concert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "concert_id")
    private Long concertId;

    @Column(nullable = false, name = "schedule_started_at")
    private LocalDateTime scheduleStaredtAt;

    @Column(nullable = false, name = "schedule_ended_at")
    private LocalDateTime scheduleEndedAt;

    public static List<Schedule> validateSchedules(List<Schedule> schedules) {
        if (schedules == null || schedules.isEmpty()) {
            throw new IllegalArgumentException("콘서트 일정이 존재하지 않습니다.");
        }
        return schedules;
    }
}
