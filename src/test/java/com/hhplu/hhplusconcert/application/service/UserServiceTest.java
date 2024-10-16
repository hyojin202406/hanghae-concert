package com.hhplu.hhplusconcert.application.service;

import com.hhplu.hhplusconcert.app.application.service.UserService;
import com.hhplu.hhplusconcert.app.domain.user.User;
import com.hhplu.hhplusconcert.app.infrastructure.user.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserJpaRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void 유저가_존재하지않으면_예외처리() {

        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(null)); // 빈 리스트로 반환

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.user(userId));

        // Then
        assertEquals("User not found", exception.getMessage());

    }

    @Test
    void 유저_조회_성공() {
        // Given
        Long userId = 1L;
        User user = User.builder()
                        .id(1L)
                        .name("userId")
                        .password("password")
                        .build();
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user)); // 빈 리스트로 반환

        // When
        User result = userService.user(userId);

        // Then
        assertEquals(userId, result.getId());
        assertEquals("userId", result.getName());
        assertEquals("password", result.getPassword());
    }
}