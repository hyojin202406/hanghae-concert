package com.hhplu.hhplusconcert.application;

import com.hhplu.hhplusconcert.domain.queue.QueueEntity;
import com.hhplu.hhplusconcert.domain.user.UserEntity;
import com.hhplu.hhplusconcert.infrastructure.queue.QueueRepository;
import com.hhplu.hhplusconcert.infrastructure.queue.QueueStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {

    @Mock
    QueueRepository queueRepository;

    @InjectMocks
    QueueService queueService;

    @Test
    void 토큰_생성_성공() {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .name("user")
                .password("1234")
                .build();
        QueueEntity queue = new QueueEntity();

        String token = UUID.nameUUIDFromBytes(user.getName().getBytes()).toString();

        // When
        queue.token(user);

        // Then
        assertThat(queue.getQueueToken()).isEqualTo(token);
    }

    @Test
    void Queue_생성_성공() {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .name("user")
                .password("1234")
                .build();

        // Mocking QueueRepository
        QueueEntity expectedQueue = QueueEntity.builder()
                .queueToken("d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a5")
                .userId(user.getId())
                .queueStatus(QueueStatus.WAITING)
                .issuedAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(10))
                .build();

        // Mocking the save method to return expectedQueue
        when(queueRepository.save(any(QueueEntity.class))).thenReturn(expectedQueue);

        // When
        QueueEntity result = queueService.token(user);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getQueueToken()).isEqualTo(expectedQueue.getQueueToken());
        assertThat(result.getUserId()).isEqualTo(user.getId());
        assertThat(result.getQueueStatus()).isEqualTo(QueueStatus.WAITING);

    }

}