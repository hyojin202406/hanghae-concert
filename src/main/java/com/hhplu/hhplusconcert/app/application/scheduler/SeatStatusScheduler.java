package com.hhplu.hhplusconcert.app.application.scheduler;

import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SeatStatusScheduler {

    private final SeatRepository seatRepository;

    @Scheduled(cron = "0 0/1 * * * *")
    public void updateSeatStatus() {
        LocalDateTime now = LocalDateTime.now();
        // 임시 예약 상태인 좌석 중에서 expiredAt이 현재 시간보다 이전인 좌석을 조회
        List<Seat> expiredSeats = seatRepository.findAllByStatusAndExpiredAtBefore(SeatStatus.TEMPORARILY_RESERVED, now);

        expiredSeats.forEach(seat -> {
            seat.changeStatus(SeatStatus.AVAILABLE);
            seat.changeReservationId(null);
        });

    }
}