package com.hhplu.hhplusconcert.application;

import com.hhplu.hhplusconcert.application.service.QueueService;
import com.hhplu.hhplusconcert.domain.queue.Queue;
import com.hhplu.hhplusconcert.domain.user.User;
import com.hhplu.hhplusconcert.infrastructure.queue.QueueRepository;
import com.hhplu.hhplusconcert.infrastructure.queue.QueueStatus;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {

    @Mock
    QueueRepository queueRepository;

    @InjectMocks
    QueueService queueService;

    @Nested
    class 유저_토큰_발급 {
        @Test
        void 토큰_생성_성공() {
            // Given
            User user = User.builder()
                    .id(1L)
                    .name("user")
                    .password("1234")
                    .build();
            Queue queue = new Queue();

            String token = UUID.nameUUIDFromBytes(user.getName().getBytes()).toString();

            // When
            queue.token(user);

            // Then
            assertThat(queue.getQueueToken()).isEqualTo(token);
        }

        @Test
        void Queue_생성_성공() {
            // Given
            User user = User.builder()
                    .id(1L)
                    .name("user")
                    .password("1234")
                    .build();

            Queue expectedQueue = Queue.builder()
                    .queueToken("d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a5")
                    .userId(user.getId())
                    .queueStatus(QueueStatus.WAITING)
                    .issuedAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now().plusMinutes(10))
                    .build();

            when(queueRepository.save(any(Queue.class))).thenReturn(expectedQueue);

            // When
            Queue result = queueService.token(user);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getQueueToken()).isEqualTo(expectedQueue.getQueueToken());
            assertThat(result.getUserId()).isEqualTo(user.getId());
            assertThat(result.getQueueStatus()).isEqualTo(QueueStatus.WAITING);
        }
    }

    @Nested
    class 대기열_토큰_조회 {
        @Test
        void 대기열_토큰_조회_실패() {
            // Given
            String queueToken = "0000-8946-4a57-9eaf-cb7f48e8c1a5";
            when(queueRepository.findByQueueToken(any(String.class))).thenReturn(Optional.empty());

            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> queueService.getToken(queueToken),
                    "Queue token not found");

            // Then
            assertEquals("Queue token not found", exception.getMessage());
        }

        @Test
        void 대기열_토큰_조회_성공() {
            // Given
            String queueToken = "0000-8946-4a57-9eaf-cb7f48e8c1a5";
            Queue queue = Queue.builder()
                    .id(1L)
                    .queueToken(queueToken)
                    .userId(1L)
                    .issuedAt(LocalDateTime.of(2024, 10, 8, 10, 0, 0))
                    .expiredAt(LocalDateTime.of(2024, 10, 8, 10, 10, 0))
                    .build();
            when(queueRepository.findByQueueToken(any(String.class))).thenReturn(Optional.of(queue));

            // When
            Queue result = queueService.getToken(queueToken);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getQueueToken()).isEqualTo(queueToken);
            assertThat(result.getUserId()).isEqualTo(1L);
            assertThat(result.getIssuedAt())
                    .isEqualTo(LocalDateTime.of(2024, 10, 8, 10, 0, 0));
            assertThat(result.getExpiredAt())
                    .isEqualTo(LocalDateTime.of(2024, 10, 8, 10, 10, 0));
        }
    }


}