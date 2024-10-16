package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.infrastructure.concert.SeatJpaRepository;
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
class SeatServiceTest {

    @Mock
    SeatJpaRepository seatRepository;

    @InjectMocks
    SeatService seatService;

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
                    () -> seatService.seats(scheduleId),
                    "좌석을 찾을 수 없습니다.");

            assertThat(exception.getMessage()).isEqualTo("좌석을 찾을 수 없습니다.");
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
            List<Seat> response = seatService.seats(scheduleId);

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
            List<Seat> response = seatService.seats(concertId, scheduleId);

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