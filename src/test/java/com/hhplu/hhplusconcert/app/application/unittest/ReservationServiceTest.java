package com.hhplu.hhplusconcert.app.application.unittest;

import com.hhplu.hhplusconcert.app.application.service.reservation.service.ReservationService;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationStatus;
import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;
import com.hhplu.hhplusconcert.app.domain.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void 임시_예약_저장_성공() {
        // Given
        Long userId = 1L;
        Reservation reservation = Reservation.builder()
                .userId(userId)
                .paymentId(null)
                .reservedAt(LocalDateTime.now())
                .reservationStatus(ReservationStatus.TEMPORARY_RESERVED)
                .build();

        // When
        when(reservationRepository.saveReservation(any(Reservation.class)))
                .thenReturn(reservation);

        Reservation createdReservation = reservationService.createReservation(userId);

        // Then
        assertEquals(ReservationStatus.TEMPORARY_RESERVED, createdReservation.getReservationStatus());
        assertEquals(userId, createdReservation.getUserId());
        verify(reservationRepository).saveReservation(any(Reservation.class));
    }
}
