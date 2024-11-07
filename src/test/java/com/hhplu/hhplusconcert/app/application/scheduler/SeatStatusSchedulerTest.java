package com.hhplu.hhplusconcert.app.application.scheduler;

import com.hhplu.hhplusconcert.app.domain.concert.SeatStatus;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.concert.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SeatStatusSchedulerTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatStatusScheduler seatStatusScheduler;

    @Test
    void 임시_배정_시간이_지나면_좌석_상태를_활성화한다() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Seat seat1 = mock(Seat.class);
        Seat seat2 = mock(Seat.class);
        List<Seat> list = List.of(seat1, seat2);
        when(seatRepository.findAllByStatusAndExpiredAtBefore(eq(SeatStatus.TEMPORARILY_RESERVED), any(LocalDateTime.class))).thenReturn(list);

        // When
        seatStatusScheduler.updateSeatStatus();

        // Then
        list.forEach(seat -> {
            verify(seat).changeStatus(SeatStatus.AVAILABLE);
            verify(seat).changeReservationId(null);
        });

        // verify 함수는 Mockito에서 mock 객체의 동작을 검증하는 데 사용합니다.
        // 실제로 검증하려는 객체가 mock 객체가 아니면 오류가 발생합니다.
        verify(seatRepository).findAllByStatusAndExpiredAtBefore(eq(SeatStatus.TEMPORARILY_RESERVED), any(LocalDateTime.class));
    }
}
