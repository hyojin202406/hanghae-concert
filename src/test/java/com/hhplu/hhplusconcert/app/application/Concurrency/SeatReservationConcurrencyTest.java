package com.hhplu.hhplusconcert.app.application.concurrency;

import com.hhplu.hhplusconcert.app.application.facade.ReservationFacade;
import com.hhplu.hhplusconcert.app.application.service.reservation.dto.ReserveSeatsDto;
import com.hhplu.hhplusconcert.app.application.service.reservation.dto.ReserveSeatsResponseDto;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.hhplu.hhplusconcert.app.domain.concert.SeatStatus.TEMPORARILY_RESERVED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@Transactional
public class SeatReservationConcurrencyTest {

    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private SeatRepository seatRepository;

    private Long concertId = 1L;
    private Long scheduleId = 1L;
    private Long[] seatIds = {1L, 2L};

    @Test
    public void 좌석_예약_동시성_제어() throws Exception {
        // Given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Future<ReserveSeatsResponseDto>> futures = new ArrayList<>();

        // When
        for (int i = 0; i < threadCount; i++) {
            final Long userId = (long) i + 1;
            ReserveSeatsDto command = new ReserveSeatsDto(userId, concertId, scheduleId, seatIds);
            Future<ReserveSeatsResponseDto> future = executorService.submit(() -> {
                try {
                    return reservationFacade.reserveSeats(command);
                } catch (Exception e) {
                    log.error("Reservation failed: {}", e.getMessage());
                    return null;
                }
            });
            futures.add(future);
        }

        // 성공 및 실패 카운터
        int successCount = 0;
        int failureCount = 0;

        // 스레드가 완료될 때까지 대기
        for (Future<ReserveSeatsResponseDto> future : futures) {
            ReserveSeatsResponseDto reservation = future.get();
            if (reservation != null) {
                successCount++;
                log.info("Reservation successful: {}", reservation.getReservationId());
            } else {
                failureCount++;
            }
        }
        log.info("successCount: {}", successCount);
        log.info("failureCount: {}", failureCount);

        // Then
        assertEquals(1, successCount);
        assertEquals(99, failureCount);

        // 모든 스레드가 작업을 완료한 후 예약 상태 확인
        List<Seat> reservedSeats = seatRepository.findSeatsByScheduleId(seatIds, scheduleId);
        reservedSeats.forEach(seat -> {
            assertEquals(seat.getStatus(), TEMPORARILY_RESERVED);
        });

        executorService.shutdown();
    }

}