package com.hhplu.hhplusconcert.application;

import com.hhplu.hhplusconcert.application.service.ConcertService;
import com.hhplu.hhplusconcert.domain.concert.Schedule;
import com.hhplu.hhplusconcert.infrastructure.concert.ConcertRepository;
import com.hhplu.hhplusconcert.infrastructure.concert.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {
    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    ConcertRepository concertRepository;

    @InjectMocks
    ConcertService concertService;

    @Test
    void 콘서트_일정_조회_실패시_예외처리() {
        // Given
        Long concertId = 1L;
        String QUEUE_TOKEN= "d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a5";

        when(scheduleRepository.findByConcertId(concertId)).thenReturn(null);

        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> concertService.schedule(concertId),
                "Schedule not found");
    }

    /**
     * {
     *   "concertId": 1,
     *   "events": [
     *     {
     *       "scheduleId": 1,
     *       "concertAt": "2024-10-08T10:00:00"
     *     },
     *     {
     *       "scheduleId": 2,
     *       "concertAt": "2024-10-08T12:00:00"
     *     }
     *   ]
     * }
     */
    @Test
    void 콘서트_일정_조회_성공() {
        // Given
        Long concertId = 1L;
        List<Schedule> schedules = List.of(
                Schedule.builder().id(1L).concertId(1L).scheduleStaredtAt(LocalDateTime.of(2024, 10, 14, 10, 0)).scheduleEndedAt(LocalDateTime.of(2024, 10, 14, 12, 0)).build(),
                Schedule.builder().id(2L).concertId(1L).scheduleStaredtAt(LocalDateTime.of(2024, 11, 15, 10, 0)).scheduleEndedAt(LocalDateTime.of(2024, 11, 15, 12, 0)).build(),
                Schedule.builder().id(3L).concertId(1L).scheduleStaredtAt(LocalDateTime.of(2024, 12, 16, 10, 0)).scheduleEndedAt(LocalDateTime.of(2024, 12, 16, 12, 0)).build(),
                Schedule.builder().id(4L).concertId(1L).scheduleStaredtAt(LocalDateTime.of(2025, 1, 17, 10, 0)).scheduleEndedAt(LocalDateTime.of(2025, 1, 17, 12, 0)).build()
        );

        when(scheduleRepository.findByConcertId(concertId)).thenReturn(schedules);

        // When
        List<Schedule> schedule = concertService.schedule(concertId);

        // Then
        assertThat(schedule).hasSize(4);
        assertThat(schedule.get(0).getScheduleStaredtAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 10, 0));
        assertThat(schedule.get(0).getScheduleEndedAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 12, 0));
    }

}