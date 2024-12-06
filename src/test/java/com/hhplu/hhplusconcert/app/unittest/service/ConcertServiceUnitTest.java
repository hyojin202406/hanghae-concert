package com.hhplu.hhplusconcert.app.unittest.service;

import com.hhplu.hhplusconcert.app.application.service.concert.service.ScheduleService;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import com.hhplu.hhplusconcert.app.domain.concert.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcertServiceUnitTest {

    @Mock
    ScheduleRepository scheduleRepository;

    @InjectMocks
    ScheduleService scheduleService;

    @Test
    void 콘서트_일정_조회_결과가_없을_때_빈_리스트_반환() {
        // Given
        Long concertId = 1L;
        when(scheduleRepository.getSchedulesByConcertId(concertId))
                .thenReturn(new ArrayList<>()); // 빈 리스트 반환

        // When
        List<Schedule> schedules = scheduleService.schedule(concertId);

        // Then
        assertThat(schedules).isNotNull(); // 반환값이 null이 아님을 확인
        assertThat(schedules).isEmpty();  // 리스트가 비어 있는지 확인
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

        when(scheduleRepository.getSchedulesByConcertId(concertId)).thenReturn(schedules);

        // When
        List<Schedule> result = scheduleService.schedule(concertId);

        // Then
        assertThat(schedules).isNotEmpty();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getScheduleStaredtAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 10, 0));
        assertThat(result.get(0).getScheduleEndedAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 12, 0));
        verify(scheduleRepository, times(1)).getSchedulesByConcertId(concertId);
    }
}