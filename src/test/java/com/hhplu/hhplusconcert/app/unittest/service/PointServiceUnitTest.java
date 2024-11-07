package com.hhplu.hhplusconcert.app.unittest.service;

import com.hhplu.hhplusconcert.app.application.service.point.command.GetPointCommand;
import com.hhplu.hhplusconcert.app.application.service.point.service.PointService;
import com.hhplu.hhplusconcert.app.application.service.point.command.RechargeCommand;
import com.hhplu.hhplusconcert.app.domain.point.entity.Point;
import com.hhplu.hhplusconcert.app.domain.point.repository.PointRepository;
import com.hhplu.hhplusconcert.common.exception.BaseException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceUnitTest {

    @Mock
    PointRepository pointRepository;

    @InjectMocks
    PointService pointService;

    @Nested
    class 잔액_충전 {
        @Test
        void 잔액_충전_금액이_0이하일_때_예외처리() {
            // Given
            Long userId = 1L;
            long rechargeAmount = -1000;

            // when
            when(pointRepository.point(any(Long.class))).thenReturn(Point.builder().pointAmount(BigDecimal.valueOf(100)).build());

            // When
            BaseException exception = assertThrows(BaseException.class, () ->
                    pointService.rechargePoint(new RechargeCommand(userId, rechargeAmount)));

            // Then
            assertEquals("Invalid request", exception.getMessage());
        }

        @Test
        void 잔액_충전_성공() {
            // Given
            Long userId = 1L;
            long rechargeAmount = 50000L;
            Point point = Point.builder()
                    .id(1L)
                    .userId(userId)
                    .pointAmount(BigDecimal.valueOf(10000))
                    .build();

            when(pointRepository.point(userId)).thenReturn(point);

            // When
            GetPointCommand command = pointService.rechargePoint(new RechargeCommand(userId, rechargeAmount));

            // Then
            assertEquals(BigDecimal.valueOf(60000), command.getCurrentPointAmount()); // 10000 + 50000
            assertEquals(BigDecimal.valueOf(60000), point.getPointAmount());
        }
    }

    @Nested
    class 잔액_조회 {
        @Test
        void 잔액_조회_성공() {
            // Given
            Long userId = 1L;

            Point point = Point.builder()
                    .id(1L)
                    .userId(userId)
                    .pointAmount(BigDecimal.valueOf(10000))
                    .build();

            when(pointRepository.point(userId)).thenReturn(point);

            // When
            GetPointCommand command = pointService.getPoint(userId);

            // Then
            assertEquals(BigDecimal.valueOf(10000), command.getCurrentPointAmount()); // 잔액이 10000인지 확인
        }

        @Test
        void 잔액_조회_유저가_존재하지않으면_예외처리() {
            // Given
            Long userId = 9999L;
            when(pointRepository.point(userId)).thenThrow(new IllegalArgumentException("유저를 찾을 수 없습니다."));

            // When
            IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, () -> pointService.getPoint(userId));

            // Then
            assertEquals("유저를 찾을 수 없습니다.", exception.getMessage());
        }
    }
}