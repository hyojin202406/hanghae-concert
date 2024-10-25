package com.hhplu.hhplusconcert.app.unittest.service;

import com.hhplu.hhplusconcert.app.application.service.concert.service.ConcertService;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Concert;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import com.hhplu.hhplusconcert.app.domain.concert.repository.ConcertRepository;
import com.hhplu.hhplusconcert.app.domain.concert.repository.ScheduleRepository;
import com.hhplu.hhplusconcert.common.exception.BaseException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    ConcertRepository concertRepository;

    @InjectMocks
    ConcertService concertService;

    @Nested
    class 콘서트_일정_조회 {
        @Test
        void 콘서트_일정_조회_실패시_예외처리() {
            Long concertId = 1L;
            when(concertRepository.existsConcert(concertId)).thenReturn(Optional.empty()); // 콘서트가 존재하지 않음

            // When & Then
            BaseException exception = assertThrows(BaseException.class,
                    () -> concertService.schedule(concertId));

            assertThat(exception.getMessage()).isEqualTo("Not found");
        }

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
            when(concertRepository.existsConcert(concertId)).thenReturn(Optional.of(Concert.builder().id(concertId).build()));
            when(scheduleRepository.existsSchedule(concertId)).thenReturn(schedules);

            // When
            List<Schedule> result = concertService.schedule(concertId);

            // Then
            assertThat(schedules).isNotEmpty();
            assertThat(result).isNotEmpty();
            assertThat(result.get(0).getScheduleStaredtAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 10, 0));
            assertThat(result.get(0).getScheduleEndedAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 12, 0));
            verify(scheduleRepository, times(1)).existsSchedule(concertId);
        }
    }
}