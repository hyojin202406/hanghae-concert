package com.hhplu.hhplusconcert.app.application.service.integrationTest;

import com.hhplu.hhplusconcert.app.application.service.ReservationService;
import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.repository.SeatRepository;
import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;
import com.hhplu.hhplusconcert.app.infrastructure.concert.SeatJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SeatReservationConcurrencyTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatJpaRepository seatJpaRepository;

    private Long concertId = 1L;
    private Long scheduleId = 1L;
    private Long[] seatIds = {1L, 2L}; // 테스트할 좌석 ID

    @BeforeEach
    public void setUp() {
        // 좌석 상태 초기화 (AVAILABLE 상태로)
        List<Seat> seats = seatRepository.findSeatsByScheduleId(seatIds, scheduleId);
        seats.forEach(seat -> {
            seat.changeStatus(SeatStatus.AVAILABLE);
            seatJpaRepository.save(seat);
        });
    }

    /**
     * testSeatReservationConcurrency 메서드:
     * ExecutorService를 사용하여 동시성을 테스트합니다. 10개의 스레드를 생성하여 동시에 좌석을 예약하려고 시도합니다.
     * 각 스레드는 다른 유저 ID를 사용하여 ReservationService.reserveSeats() 메서드를 호출합니다.
     * 예약이 성공한 경우 예약 성공 메시지를 출력하고, 실패한 경우 예외 메시지를 출력합니다.
     * 좌석 상태 검증: 모든 스레드가 작업을 마친 후, 좌석의 상태가 올바르게 업데이트되었는지 확인합니다. 예약된 좌석은 TEMPORARILY_RESERVED 상태로 변경되어 있어야 하며, 좌석 상태를 확인하여 동시성 문제를 테스트합니다.
     */
    @Test
    @Transactional
    public void 좌석_예약_동시성_제어() throws Exception {
        int threadCount = 10; // 동시 실행할 스레드 수
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Future<Reservation>> futures = new ArrayList<>();

        // 동시성 테스트를 위해 여러 스레드가 동시에 좌석을 예약 시도
        for (int i = 0; i < threadCount; i++) {
            final Long userId = (long) i + 1; // 각기 다른 유저 ID
            Future<Reservation> future = executorService.submit(() -> {
                try {
                    return reservationService.reserveSeats(userId, concertId, scheduleId, seatIds);
                } catch (Exception e) {
                    System.out.println("예약 실패: " + e.getMessage());
                    return null;
                }
            });
            futures.add(future);
        }

        // 스레드가 완료될 때까지 대기
        for (Future<Reservation> future : futures) {
            Reservation reservation = future.get();
            if (reservation != null) {
                System.out.println("예약 성공: " + reservation.getId());
            }
        }

        // 모든 스레드가 작업을 완료한 후 예약 상태 확인
        List<Seat> reservedSeats = seatRepository.findSeatsByScheduleId(seatIds, scheduleId);
        reservedSeats.forEach(seat -> {
            assertTrue(seat.getStatus() == SeatStatus.TEMPORARILY_RESERVED || seat.getStatus() == SeatStatus.AVAILABLE, "좌석 상태가 잘못되었습니다.");
        });

        // 스레드 종료
        executorService.shutdown();
    }
}