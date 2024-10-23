package com.hhplu.hhplusconcert.app.application.scheduler;

import com.hhplu.hhplusconcert.app.domain.waitingqueue.repository.WaitingQueueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WaitingQueueStatusSchedulerTest {

    @Mock
    private WaitingQueueRepository queueRepository;

    @InjectMocks
    private WaitingQueueStatusScheduler waitingQueueStatusScheduler;

    @Test
    void 대기열_상태를_갱신한다() {
        // When
        waitingQueueStatusScheduler.updateQueueStatus();

        // Then
        verify(queueRepository).deactivateStatus(); // deactivateStatus() 메서드 호출 여부 검증
        verify(queueRepository).activateStatus();   // activateStatus() 메서드 호출 여부 검증
    }
}