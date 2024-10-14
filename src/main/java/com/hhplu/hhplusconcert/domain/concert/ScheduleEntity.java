package com.hhplu.hhplusconcert.domain.concert;

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
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "concert_id")
    private String concertId;

    @Column(nullable = false, name = "scheduled_start_at")
    private LocalDateTime scheduledStartAt;

    @Column(nullable = false, name = "scheduled_end_at")
    private LocalDateTime scheduledEndAt;

    public static List<ScheduleEntity> validateSchedules(List<ScheduleEntity> schedules) {
        if (schedules == null || schedules.isEmpty()) {
            throw new IllegalArgumentException("Schedule not found");
        }
        return schedules;
    }
}
