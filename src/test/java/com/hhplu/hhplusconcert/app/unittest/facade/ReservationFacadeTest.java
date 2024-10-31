package com.hhplu.hhplusconcert.app.unittest.facade;

import com.hhplu.hhplusconcert.app.application.facade.ReservationFacade;
import com.hhplu.hhplusconcert.app.application.service.concert.service.ConcertService;
import com.hhplu.hhplusconcert.app.application.service.concert.service.SeatService;
import com.hhplu.hhplusconcert.app.application.service.payment.service.PaymentService;
import com.hhplu.hhplusconcert.app.application.service.reservation.command.ReserveSeatsCommand;
import com.hhplu.hhplusconcert.app.application.service.reservation.command.ReserveSeatsResponseCommand;
import com.hhplu.hhplusconcert.app.application.service.reservation.service.ReservationService;
import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Concert;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationStatus;
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

        Concert concert = new Concert(); // 콘서트 객체를 생성
        Reservation reservation = Reservation.builder().id(1L).reservationStatus(ReservationStatus.TEMPORARY_RESERVED).build(); // 예약 객체를 생성

        // Mock 설정
        when(concertService.validateConcertExists(concertId)).thenReturn(concert);
        when(reservationService.createReservation(userId)).thenReturn(reservation);

        Seat seat1 = Seat.builder().id(1L).seatPrice(10000L).status(SeatStatus.AVAILABLE).build(); // Seat 객체 생성
        Seat seat2 = Seat.builder().id(2L).seatPrice(10000L).status(SeatStatus.AVAILABLE).build(); // Seat 객체 생성
//        Seat seat2 = new Seat(); // Seat 객체 생성
        List<Seat> seats1 = List.of(seat1, seat2); // 좌석 목록 생성

        when(seatService.existSeats(scheduleId, seatIds)).thenReturn(seats1);
        when(seatService.updateSeatStatus(anyLong(), anyLong(), any(Long[].class), any())).thenReturn(seats1);

        // When
        ReserveSeatsResponseCommand result = reservationFacade.reserveSeats(new ReserveSeatsCommand(userId, concertId, scheduleId, seatIds));

        // Then
        assertNotNull(result); // 결과가 null이 아님을 확인
        // 추가적인 검증을 위해 result의 상태를 확인할 수 있습니다.
    }

    @Test
    public void 좌석_예약_실패() {
        // Given
        Long userId = 1L;
        Long concertId = 1L;
        Long scheduleId = 1L;
        Long[] seatIds = {1L, 2L};

        Concert mockConcert = new Concert(); // 필요한 경우, mock 객체의 필드를 초기화합니다.
        when(concertService.validateConcertExists(concertId)).thenReturn(mockConcert);
        when(reservationService.createReservation(userId)).thenThrow(new OptimisticLockException());

        // When & Then
        OptimisticLockException exception = assertThrows(OptimisticLockException.class, () -> {
            reservationFacade.reserveSeats(new ReserveSeatsCommand(userId, concertId, scheduleId, seatIds));
        });

        assertEquals(null, exception.getMessage());
        verify(reservationService).createReservation(userId);
    }
}