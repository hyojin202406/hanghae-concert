package com.hhplu.hhplusconcert.app.application.token;

import com.hhplu.hhplusconcert.app.application.concert.service.SeatService;
import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.repository.SeatRepository;
import org.junit.jupiter.api.Nested;
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
class SeatServiceTest {

    @Mock
    SeatRepository seatRepository;

    @InjectMocks
    SeatService seatService;

    @Nested
    class 콘서트_좌석_조회 {
        @Test
        void 콘서트_좌석_모두_조회_실패_예외처리() {
            // Given
            Long concertId = 1L;
            Long scheduleId = 2L;

            when(seatRepository.getAllSeatsByScheduleId(scheduleId)).thenThrow(new IllegalArgumentException("좌석을 찾을 수 없습니다."));

            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> seatService.getAllSeatsByScheduleId(scheduleId),
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

            when(seatRepository.getAllSeatsByScheduleId(scheduleId)).thenReturn(allSeats);


            // When
            List<Seat> response = seatService.getAllSeatsByScheduleId(scheduleId);

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

            when(seatRepository.getAvailableSeatsByScheduleId(scheduleId, SeatStatus.AVAILABLE)).thenReturn(availableSeats);

            // When
            List<Seat> response = seatService.getAvailableSeatsByScheduleId(scheduleId);

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