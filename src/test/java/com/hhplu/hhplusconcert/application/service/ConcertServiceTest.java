package com.hhplu.hhplusconcert.application.service;

import com.hhplu.hhplusconcert.domain.concert.Schedule;
import com.hhplu.hhplusconcert.domain.concert.Seat;
import com.hhplu.hhplusconcert.infrastructure.concert.ConcertRepository;
import com.hhplu.hhplusconcert.infrastructure.concert.ScheduleRepository;
import com.hhplu.hhplusconcert.infrastructure.concert.SeatRepository;
import com.hhplu.hhplusconcert.domain.concert.SeatStatus;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    ConcertRepository concertRepository;

    @Mock
    SeatRepository seatRepository;

    @InjectMocks
    ConcertService concertService;

    @Nested
    class 콘서트_일정_조회 {
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

    @Nested
    class 콘서트_좌석_조회 {
        @Test
        void 콘서트_좌석_모두_조회_실패_예외처리() {
            // Given
            Long concertId = 1L;
            Long scheduleId = 2L;

            when(seatRepository.findByScheduleId(scheduleId)).thenReturn(Optional.empty());

            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> concertService.seats(concertId),
                    "좌석을 찾을 수 없습니다.");

            assertThat(exception.getMessage()).isEqualTo("Schedule not found");
        }

        @Test
        void 콘서트_좌석_모두_조회_성공() {
            // Given
            Long scheduleId = 2L;
            List<Seat> allSeats = List.of(
                    Seat.builder()
                            .id(1L).scheduleId(scheduleId).seatNumber(1L).seatPrice(50000L).status(SeatStatus.AVAILABLE)
                            .createdAt(LocalDateTime.of(2024, 10, 14, 10, 0))
                            .expiredAt(LocalDateTime.of(2024, 10, 14, 10, 5))
                            .build(),
                    Seat.builder()
                            .id(2L).scheduleId(scheduleId).seatNumber(2L).seatPrice(100000L).status(SeatStatus.AVAILABLE)
                            .createdAt(LocalDateTime.of(2024, 10, 14, 10, 0))
                            .expiredAt(LocalDateTime.of(2024, 10, 14, 10, 5))
                            .build(),
                    Seat.builder()
                            .id(3L).scheduleId(scheduleId).seatNumber(3L).seatPrice(200000L).status(SeatStatus.RESERVED)
                            .createdAt(LocalDateTime.of(2024, 10, 14, 10, 0))
                            .expiredAt(LocalDateTime.of(2024, 10, 14, 10, 5))
                            .build()
            );

            // Optional<List<Seat>>로 반환하도록 수정
            when(seatRepository.findByScheduleId(scheduleId)).thenReturn(Optional.of(allSeats));


            // When
            List<Seat> response = concertService.seats(scheduleId);

            // Then
            assertThat(response).hasSize(3);

            // 첫 번째 좌석 검증
            assertThat(response.get(0).getId()).isEqualTo(1L);
            assertThat(response.get(0).getScheduleId()).isEqualTo(scheduleId);
            assertThat(response.get(0).getSeatNumber()).isEqualTo(1L);
            assertThat(response.get(0).getSeatPrice()).isEqualTo(50000L);
            assertThat(response.get(0).getStatus()).isEqualTo(SeatStatus.AVAILABLE);
            assertThat(response.get(0).getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 10, 0));
            assertThat(response.get(0).getExpiredAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 10, 5));

            // 두 번째 좌석 검증
            assertThat(response.get(1).getId()).isEqualTo(2L);
            assertThat(response.get(1).getScheduleId()).isEqualTo(scheduleId);
            assertThat(response.get(1).getSeatNumber()).isEqualTo(2L);
            assertThat(response.get(1).getSeatPrice()).isEqualTo(100000L);
            assertThat(response.get(1).getStatus()).isEqualTo(SeatStatus.AVAILABLE);
            assertThat(response.get(1).getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 10, 0));
            assertThat(response.get(1).getExpiredAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 10, 5));

            // 세 번째 좌석 검증
            assertThat(response.get(2).getId()).isEqualTo(3L);
            assertThat(response.get(2).getScheduleId()).isEqualTo(scheduleId);
            assertThat(response.get(2).getSeatNumber()).isEqualTo(3L);
            assertThat(response.get(2).getSeatPrice()).isEqualTo(200000L);
            assertThat(response.get(2).getStatus()).isEqualTo(SeatStatus.RESERVED);
            assertThat(response.get(2).getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 10, 0));
            assertThat(response.get(2).getExpiredAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 10, 5));
        }

        @Test
        void 콘서트_예약가능_좌석__조회_성공() {
            // Given
            Long concertId = 1L;
            Long scheduleId = 2L;
            List<Seat> availableSeats = List.of(
                    Seat.builder()
                            .id(1L).scheduleId(scheduleId).seatNumber(1L).seatPrice(50000L).status(SeatStatus.AVAILABLE)
                            .createdAt(LocalDateTime.of(2024, 10, 14, 10, 0))
                            .expiredAt(LocalDateTime.of(2024, 10, 14, 10, 5))
                            .build(),
                    Seat.builder()
                            .id(2L).scheduleId(scheduleId).seatNumber(2L).seatPrice(100000L).status(SeatStatus.AVAILABLE)
                            .createdAt(LocalDateTime.of(2024, 10, 14, 10, 0))
                            .expiredAt(LocalDateTime.of(2024, 10, 14, 10, 5))
                            .build()
            );

            when(seatRepository.findByScheduleIdAndStatus(scheduleId, SeatStatus.AVAILABLE)).thenReturn(Optional.of(availableSeats));

            // When
            List<Seat> response = concertService.seats(concertId, scheduleId);

            // Then
            // 첫 번째 좌석 검증
            assertThat(response.get(0).getId()).isEqualTo(1L);
            assertThat(response.get(0).getScheduleId()).isEqualTo(scheduleId);
            assertThat(response.get(0).getSeatNumber()).isEqualTo(1L);
            assertThat(response.get(0).getSeatPrice()).isEqualTo(50000L);
            assertThat(response.get(0).getStatus()).isEqualTo(SeatStatus.AVAILABLE);
            assertThat(response.get(0).getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 10, 0));
            assertThat(response.get(0).getExpiredAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 10, 5));

            // 두 번째 좌석 검증
            assertThat(response.get(1).getId()).isEqualTo(2L);
            assertThat(response.get(1).getScheduleId()).isEqualTo(scheduleId);
            assertThat(response.get(1).getSeatNumber()).isEqualTo(2L);
            assertThat(response.get(1).getSeatPrice()).isEqualTo(100000L);
            assertThat(response.get(1).getStatus()).isEqualTo(SeatStatus.AVAILABLE);
            assertThat(response.get(1).getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 10, 0));
            assertThat(response.get(1).getExpiredAt()).isEqualTo(LocalDateTime.of(2024, 10, 14, 10, 5));
        }

    }
}