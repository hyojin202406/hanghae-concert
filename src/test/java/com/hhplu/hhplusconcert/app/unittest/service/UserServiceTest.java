package com.hhplu.hhplusconcert.app.unittest.service;

import com.hhplu.hhplusconcert.app.application.service.user.service.UserService;
import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import com.hhplu.hhplusconcert.app.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
                    .build();

            when(userRepository.getUser(userId)).thenReturn(user); // UserRepository의 getUser 메서드 Mocking

            // When
            User result = userService.user(userId);

            // Then
            assertEquals(userId, result.getId());
            assertEquals("userId", result.getName());
        }
    }

}