package com.hhplu.hhplusconcert.application;

import com.hhplu.hhplusconcert.domain.user.UserEntity;
import com.hhplu.hhplusconcert.infrastructure.user.UserRepository;
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
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void 회원이_존재하지않으면_예외처리() {

        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(null)); // 빈 리스트로 반환

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.user(userId));

        // Then
        assertEquals("User not found", exception.getMessage());

    }

    @Test
    void 회원_조회_성공() {
        // Given
        Long userId = 1L;
        UserEntity user = UserEntity.builder()
                        .id(1L)
                        .name("userId")
                        .password("password")
                        .build();
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user)); // 빈 리스트로 반환

        // When
        UserEntity result = userService.user(userId);

        // Then
        assertEquals(userId, result.getId());
        assertEquals("userId", result.getName());
        assertEquals("password", result.getPassword());
    }
}