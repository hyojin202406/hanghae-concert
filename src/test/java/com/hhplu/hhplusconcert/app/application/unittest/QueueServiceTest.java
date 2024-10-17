package com.hhplu.hhplusconcert.app.application.unittest;

import com.hhplu.hhplusconcert.app.application.waitingqueue.service.WaitingQueueService;
import com.hhplu.hhplusconcert.app.domain.watingqueue.WaitingQueueStatus;
import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;
import com.hhplu.hhplusconcert.app.domain.watingqueue.repository.WaitingQueueRepository;
import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {

    @Mock
    WaitingQueueRepository queueRepository;

    @InjectMocks
    WaitingQueueService queueService;


    @Nested
    class 유저_토큰_발급 {
        @Test
        void 토큰_생성_성공() {
            // Given
            User user = User.builder()
                    .id(1L)
                    .name("user")
                    .build();
            WaitingQueue queue = new WaitingQueue();

            String expectedToken = UUID.nameUUIDFromBytes(user.getName().getBytes()).toString();

            // When
            queue.token(user);

            // Then
            assertThat(queue.getQueueToken()).isEqualTo(expectedToken);
        }

        @Test
        void Queue_생성_성공() {
            // Given
            User user = User.builder()
                    .id(1L)
                    .name("user")
                    .build();

            WaitingQueue expectedQueue = WaitingQueue.builder()
                    .queueToken("d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a5")
                    .userId(user.getId())
                    .queueStatus(WaitingQueueStatus.WAITING)
                    .issuedAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now().plusMinutes(10))
                    .build();

            when(queueRepository.token(any(WaitingQueue.class))).thenReturn(expectedQueue);

            // When
            WaitingQueue result = queueService.token(user);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getQueueToken()).isEqualTo(expectedQueue.getQueueToken());
            assertThat(result.getUserId()).isEqualTo(user.getId());
            assertThat(result.getQueueStatus()).isEqualTo(WaitingQueueStatus.WAITING);
        }
    }

    @Nested
    class 대기열_토큰_조회 {
        @Test
        void 대기열_토큰_조회_실패() {
            // Given
            String queueToken = "0000-8946-4a57-9eaf-cb7f48e8c1a5";
            when(queueRepository.getToken(any(String.class))).thenThrow(new IllegalArgumentException("대기열 토큰을 찾을 수 없습니다."));

            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> queueService.getToken(queueToken),
                    "대기열 토큰을 찾을 수 없습니다.");

            // Then
            assertEquals("대기열 토큰을 찾을 수 없습니다.", exception.getMessage());
        }

        @Test
        void 대기열_토큰_조회_성공() {
            // Given
            String queueToken = "0000-8946-4a57-9eaf-cb7f48e8c1a5";
            WaitingQueue queue = WaitingQueue.builder()
                    .id(1L)
                    .queueToken(queueToken)
                    .userId(1L)
                    .issuedAt(LocalDateTime.of(2024, 10, 8, 10, 0, 0))
                    .expiredAt(LocalDateTime.of(2024, 10, 8, 10, 10, 0))
                    .build();
            when(queueRepository.getToken(any(String.class))).thenReturn(queue);

            // When
            WaitingQueue result = queueService.getToken(queueToken);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getQueueToken()).isEqualTo(queueToken);
            assertThat(result.getUserId()).isEqualTo(1L);
            assertThat(result.getIssuedAt()).isEqualTo(LocalDateTime.of(2024, 10, 8, 10, 0, 0));
            assertThat(result.getExpiredAt()).isEqualTo(LocalDateTime.of(2024, 10, 8, 10, 10, 0));
        }
    }

    @Nested
    class 활성_상태_조회 {
        @Test
        void 활성_상태_조회_성공() {
            // Given
            Long expectedId = 1L;
            when(queueRepository.getLastActiveId()).thenReturn(expectedId);

            // When
            Long actualId = queueService.getLastActiveId();

            // Then
            assertThat(actualId).isEqualTo(expectedId);
        }

        @Test
        void 활성_상태_조회값이_없을때_대기순번_0을_반환한다() {
            // Given
            when(queueRepository.getLastActiveId()).thenReturn(0L);

            // When
            Long actualId = queueService.getLastActiveId();

            // Then
            assertThat(actualId).isEqualTo(0L);
        }
    }
}