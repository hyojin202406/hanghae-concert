package com.hhplu.hhplusconcert.application.service;

import com.hhplu.hhplusconcert.domain.concert.Concert;
import com.hhplu.hhplusconcert.domain.concert.Seat;
import com.hhplu.hhplusconcert.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.domain.reservation.Reservation;
import com.hhplu.hhplusconcert.domain.reservation.ReservationStatus;
import com.hhplu.hhplusconcert.infrastructure.concert.ConcertRepository;
import com.hhplu.hhplusconcert.infrastructure.concert.SeatRepository;
import com.hhplu.hhplusconcert.infrastructure.reservation.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    ConcertRepository concertRepository;

    @Mock
    SeatRepository seatRepository;

    @Mock
    ReservationRepository reservationRepository;

    @InjectMocks
    ReservationService reservationService;

    private Long userId;
    private Long concertId;
    private Long scheduleId;
    private Long[] seatIds;
    private Concert concert;
    private List<Seat> seats;

    @BeforeEach
    void setUp() {
        userId = 1L;
        concertId = 1L;
        scheduleId = 1L;
        seatIds = new Long[]{1L, 2L};
        concert = new Concert(concertId, "Sample Concert", LocalDateTime.now());
        seats = Arrays.asList(
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
    }

    @Test
    void 예약_실패_콘서트_존재하지_않음() {
        // Given
        when(concertRepository.findById(concertId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.reserveSeats(userId, concertId, scheduleId, seatIds));

        assertEquals("존재하지 않는 콘서트입니다.", exception.getMessage());
        verify(concertRepository).findById(concertId);
        verifyNoMoreInteractions(concertRepository, reservationRepository, seatRepository);
    }

    @Test
    void 예약_실패_잘못된_좌석_정보() {
        // Given
        when(concertRepository.findById(concertId)).thenReturn(Optional.of(concert));
        when(seatRepository.findAllByIdInAndScheduleId(seatIds, scheduleId)).thenReturn(List.of());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.reserveSeats(userId, concertId, scheduleId, seatIds));

        assertEquals("잘못된 좌석 정보가 포함되어 있습니다.", exception.getMessage());
        verify(concertRepository).findById(concertId);
        verify(seatRepository).findAllByIdInAndScheduleId(seatIds, scheduleId);
        verifyNoMoreInteractions(concertRepository, reservationRepository, seatRepository);
    }

    @Test
    void 예약_실패_예약할_수_없는_좌석_포함() {
        // Given
        seats.get(0).changeStatus(SeatStatus.RESERVED); // 첫 번째 좌석이 예약 불가능 상태로 변경
        when(concertRepository.findById(concertId)).thenReturn(Optional.of(concert));
        when(seatRepository.findAllByIdInAndScheduleId(seatIds, scheduleId)).thenReturn(seats);

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> reservationService.reserveSeats(userId, concertId, scheduleId, seatIds));

        assertEquals("예약할 수 없는 좌석이 포함되어 있습니다.", exception.getMessage());
        verify(concertRepository).findById(concertId);
        verify(seatRepository).findAllByIdInAndScheduleId(seatIds, scheduleId);
        verifyNoMoreInteractions(concertRepository, reservationRepository, seatRepository);
    }

    @Test
    void 예약_성공() {
        // Given
        when(concertRepository.findById(concertId)).thenReturn(Optional.of(concert));
        when(seatRepository.findAllByIdInAndScheduleId(seatIds, scheduleId)).thenReturn(seats);
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Reservation reservation = reservationService.reserveSeats(userId, concertId, scheduleId, seatIds);

        // Then
        assertNotNull(reservation);
        assertEquals(userId, reservation.getUserId());
        assertEquals(ReservationStatus.TEMPORARY_RESERVED, reservation.getReservationStatus());
        assertNull(reservation.getPaymentId());

        // 좌석 상태 변경 검증
        seats.forEach(seat -> {
            assertEquals(SeatStatus.RESERVED, seat.getStatus());
            assertEquals(reservation.getId(), seat.getReservationId());
        });

        verify(concertRepository).findById(concertId);
        verify(seatRepository).findAllByIdInAndScheduleId(seatIds, scheduleId);
        verify(reservationRepository).save(any(Reservation.class));
        verify(seatRepository).saveAll(seats);
    }

}