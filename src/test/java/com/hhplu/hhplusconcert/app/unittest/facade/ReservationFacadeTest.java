package com.hhplu.hhplusconcert.app.unittest.facade;

import com.hhplu.hhplusconcert.app.application.facade.ReservationFacade;
import com.hhplu.hhplusconcert.app.application.service.reservation.command.ReserveSeatsCommand;
import com.hhplu.hhplusconcert.app.application.service.reservation.command.ReserveSeatsResponseCommand;
import com.hhplu.hhplusconcert.app.application.service.concert.service.ConcertService;
import com.hhplu.hhplusconcert.app.application.service.payment.service.PaymentService;
import com.hhplu.hhplusconcert.app.application.service.reservation.service.ReservationService;
import com.hhplu.hhplusconcert.app.application.service.concert.service.SeatService;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationFacadeTest {

    @Mock
    private ReservationService reservationService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private SeatService seatService;

    @Mock
    private ConcertService concertService;

    @InjectMocks
    private ReservationFacade reservationFacade;

    @Test
    public void 좌석_예약_성공() {
        // Given
        Long userId = 1L;
        Long concertId = 1L;
        Long scheduleId = 1L;
        Long[] seatIds = {1L, 2L};

        doNothing().when(concertService).validateConcertExists(concertId);
        Reservation reservation = mock(Reservation.class);
        when(reservationService.createReservation(userId)).thenReturn(reservation);

        Seat seat1 = mock(Seat.class);
        Seat seat2 = mock(Seat.class);
        when(seat1.getSeatPrice()).thenReturn(100L);
        when(seat2.getSeatPrice()).thenReturn(100L);
        when(seatService.updateSeatStatus(anyLong(), anyLong(), any(Long[].class))).thenReturn(List.of(seat1, seat2));

        // When
        ReserveSeatsResponseCommand result = reservationFacade.reserveSeats(new ReserveSeatsCommand(userId, concertId, scheduleId, seatIds));

        // Then
        assertNotNull(result);
        verify(concertService).validateConcertExists(concertId);
        verify(reservationService).createReservation(userId);
        verify(seatService).updateSeatStatus(anyLong(), anyLong(), any(Long[].class));
        verify(paymentService).createPendingPayment(any(Reservation.class), eq(200L)); // Total price = 200L
    }

    @Test
    public void 좌석_예약_실패() {
        // Given
        Long userId = 1L;
        Long concertId = 1L;
        Long scheduleId = 1L;
        Long[] seatIds = {1L, 2L};

        doNothing().when(concertService).validateConcertExists(concertId);
        when(reservationService.createReservation(userId)).thenThrow(new OptimisticLockException());

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            reservationFacade.reserveSeats(new ReserveSeatsCommand(userId, concertId, scheduleId, seatIds));
        });

        assertEquals("좌석 예약에 실패했습니다. 다시 시도해 주세요.", exception.getMessage());
        verify(reservationService).createReservation(userId);
    }
}