package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.service.reservation.dto.ReserveSeatsDto;
import com.hhplu.hhplusconcert.app.application.service.reservation.dto.ReserveSeatsResponseDto;
import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.reservation.ReservationStatus;
import com.hhplu.hhplusconcert.app.interfaces.api.reservation.req.ReservationRequest;
import com.hhplu.hhplusconcert.common.exception.BaseException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReservationFacadeTest {

    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 예약_성공() {
        // Given
        Long userId = 1L;
        Long concertId = 1L;
        Long scheduleId = 1L;
        Long[] seatIds = {1L, 2L};
        // When
        ReserveSeatsResponseDto command = reservationFacade.reserveSeats(new ReservationRequest(userId, concertId, scheduleId, seatIds).toReserveSeatsCommand());


        // Then
        assertNotNull(command);
        assertEquals(ReservationStatus.TEMPORARY_RESERVED.toString(), command.getReservationStatus());

        // 좌석 상태 확인
        List<Seat> seats = entityManager.createQuery(
                        "SELECT s FROM Seat s WHERE s.reservationId = :reservationId", Seat.class)
                .setParameter("reservationId", command.getReservationId())
                .getResultList();

        assertEquals(2, seats.size());
        for (Seat seat : seats) {
            assertEquals(SeatStatus.TEMPORARILY_RESERVED.toString(), seat.getStatus().toString());
            assertEquals(command.getReservationId(), seat.getReservationId());
        }
    }

    @Test
    void 이미_예약된_좌석을_다시_예약시_예외처리() {
        // Given
        Long userId = 1L;
        Long concertId = 1L;
        Long scheduleId = 1L;
        Long[] seatIds = {1L, 2L};

        // When
        // 동시성 테스트를 위해 동일한 좌석에 대해 두 개의 예약 시도
        reservationFacade.reserveSeats(new ReservationRequest(userId, concertId, scheduleId, seatIds).toReserveSeatsCommand());

        // 엔티티 매니저를 통해 첫 번째 예약의 좌석을 강제로 잠금
        List<Seat> seats = entityManager.createQuery(
                        "SELECT s FROM Seat s WHERE s.id IN :seatIds", Seat.class)
                .setParameter("seatIds", List.of(1L, 2L))
                .setLockMode(LockModeType.OPTIMISTIC)
                .getResultList();

        // 두 번째 예약 시도 (이미 예약된 좌석을 다시 예약하려고 함)
        BaseException exception = assertThrows(BaseException.class, () -> {
            reservationFacade.reserveSeats(new ReserveSeatsDto(userId, concertId, scheduleId, seatIds));
        });

        // Then
        assertEquals("Invalid request", exception.getMessage());
    }
}