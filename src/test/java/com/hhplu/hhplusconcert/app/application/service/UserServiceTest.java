package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import com.hhplu.hhplusconcert.app.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Nested
    class 유저_조회 {
        @Test
        void 유저가_존재하지않으면_예외처리() {
            // Given
            Long userId = 1L;
            when(userRepository.getUser(userId)).thenThrow(new IllegalArgumentException("유저를 찾을 수 없습니다."));

            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.user(userId));

            // Then
            assertEquals("유저를 찾을 수 없습니다.", exception.getMessage());
        }

        @Test
        void 유저_조회_성공() {
            // Given
            Long userId = 1L;
            User user = User.builder()
                    .id(userId)
                    .name("userId")
                    .pointAmount(BigDecimal.valueOf(10000)) // 초기 잔액 추가
                    .build();

            when(userRepository.getUser(userId)).thenReturn(user); // UserRepository의 getUser 메서드 Mocking

            // When
            User result = userService.user(userId);

            // Then
            assertEquals(userId, result.getId());
            assertEquals("userId", result.getName());
            assertEquals(BigDecimal.valueOf(10000), result.getPointAmount()); // 잔액도 검증
        }
    }

    @Nested
    class 잔액_충전 {
        @Test
        void 잔액_충전_금액이_0이하일_때_예외처리() {
            // Given
            Long userId = 1L;
            BigDecimal rechargeAmount = BigDecimal.ZERO; // 0보다 작은 금액

            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    userService.rechargePoint(userId, rechargeAmount));

            // Then
            assertEquals("충전 금액은 0보다 커야 합니다.", exception.getMessage());
        }

        @Test
        void 잔액_충전_성공() {
            // Given
            Long userId = 1L;
            BigDecimal rechargeAmount = BigDecimal.valueOf(50000);
            User user = User.builder()
                    .id(userId)
                    .name("userId")
                    .pointAmount(BigDecimal.valueOf(10000)) // 기존 잔액
                    .build();

            when(userRepository.getUser(userId)).thenReturn(user);

            // When
            BigDecimal newBalance = userService.rechargePoint(userId, rechargeAmount);

            // Then
            assertEquals(BigDecimal.valueOf(60000), newBalance); // 10000 + 50000
            assertEquals(BigDecimal.valueOf(60000), user.getPointAmount());
        }
    }

    @Nested
    class 잔액_조회 {
        @Test
        void 잔액_조회_성공() {
            // Given
            Long userId = 1L;
            User user = User.builder()
                    .id(userId)
                    .name("userId")
                    .pointAmount(BigDecimal.valueOf(10000))
                    .build();

            when(userRepository.getUser(userId)).thenReturn(user);

            // When
            BigDecimal balance = userService.getPoint(userId);

            // Then
            assertEquals(BigDecimal.valueOf(10000), balance); // 잔액이 10000인지 확인
        }

        @Test
        void 잔액_조회_유저가_존재하지않으면_예외처리() {
            // Given
            Long userId = 1L;
            when(userRepository.getUser(userId)).thenThrow(new IllegalArgumentException("유저를 찾을 수 없습니다."));

            // When
            IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, () -> userService.getPoint(userId));

            // Then
            assertEquals("유저를 찾을 수 없습니다.", exception.getMessage());
        }
    }
}