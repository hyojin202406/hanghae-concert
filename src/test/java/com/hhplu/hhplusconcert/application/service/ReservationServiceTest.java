package com.hhplu.hhplusconcert.application.service;

import com.hhplu.hhplusconcert.app.application.service.ReservationService;
import com.hhplu.hhplusconcert.app.domain.concert.*;
import com.hhplu.hhplusconcert.app.domain.reservation.Reservation;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationRepository;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationStatus;
import com.hhplu.hhplusconcert.app.infrastructure.concert.ConcertJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    private ConcertJpaRepository concertJpaRepository;

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Long userId = 1L;
    private Long concertId = 1L;
    private Long scheduleId = 1L;
    private Long[] seatIds = {1L, 2L};

    @BeforeEach
    void setUp() {
        // Initialize mocks if necessary
    }

    @Test
    void 존재하지_않는_콘서트시_예외처리() {
        // Given
        Long concertId = 1L;
        when(concertJpaRepository.findById(concertId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            concertRepository.existsConcert(concertId);
        });

        assertEquals("존재하지 않는 콘서트입니다.", exception.getMessage());
    }

    @Test
    void 좌석유효성실패시예외처리() {
        // Given
        when(seatRepository.findSeatsByScheduleId(seatIds, scheduleId)).thenReturn(Collections.singletonList(new Seat()));

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                reservationService.reserveSeats(userId, concertId, scheduleId, seatIds));

        assertEquals("잘못된 좌석 정보가 포함되어 있습니다.", exception.getMessage());
    }

    @Test
    void 모든_좌석_예약_불가능시_예외처리() {
        // Given
        when(seatRepository.findSeatsByScheduleId(seatIds, scheduleId)).thenReturn(Arrays.asList(
                Seat.builder()
                        .id(1L).scheduleId(scheduleId).seatNumber(1L).seatPrice(50000L).status(SeatStatus.RESERVED)
                        .createdAt(LocalDateTime.of(2024, 10, 14, 10, 0))
                        .expiredAt(LocalDateTime.of(2024, 10, 14, 10, 5))
                        .build(),
                Seat.builder()
                        .id(2L).scheduleId(scheduleId).seatNumber(2L).seatPrice(100000L).status(SeatStatus.AVAILABLE)
                        .createdAt(LocalDateTime.of(2024, 10, 14, 10, 0))
                        .expiredAt(LocalDateTime.of(2024, 10, 14, 10, 5))
                        .build()
        ));

        // When & Then
        Exception exception = assertThrows(IllegalStateException.class, () ->
                reservationService.reserveSeats(userId, concertId, scheduleId, seatIds));

        assertEquals("예약할 수 없는 좌석이 포함되어 있습니다.", exception.getMessage());
    }

    @Test
    void 예약성공시() {
        // Given
        when(seatRepository.findSeatsByScheduleId(seatIds, scheduleId)).thenReturn(Arrays.asList(
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
        ));

        // ReservationRepository의 saveReservation 메서드의 행동 정의
        when(reservationRepository.saveReservation(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Reservation reservation = reservationService.reserveSeats(userId, concertId, scheduleId, seatIds);

        // Then
        assertNotNull(reservation);
        assertEquals(userId, reservation.getUserId());
        assertEquals(ReservationStatus.TEMPORARY_RESERVED, reservation.getReservationStatus());

        // 좌석 상태가 예약으로 변경되었는지 확인
        ArgumentCaptor<List<Seat>> seatCaptor = ArgumentCaptor.forClass(List.class);
        verify(seatRepository).saveSeats(seatCaptor.capture());
        List<Seat> savedSeats = seatCaptor.getValue();

        assertEquals(2, savedSeats.size());
        assertEquals(SeatStatus.RESERVED, savedSeats.get(0).getStatus());
        assertEquals(SeatStatus.RESERVED, savedSeats.get(1).getStatus());
    }
}