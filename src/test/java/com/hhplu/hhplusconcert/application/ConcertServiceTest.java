package com.hhplu.hhplusconcert.application;

import com.hhplu.hhplusconcert.infrastructure.concert.ConcertRepository;
import com.hhplu.hhplusconcert.infrastructure.concert.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {
    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    ConcertRepository concertRepository;

    @InjectMocks
    ConcertService concertService;

    @Test
    void 콘서트_일정_조회_실패시_예외처리() {
        // Given
        Long concertId = 1L;
        String QUEUE_TOKEN= "d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a5";

        when(scheduleRepository.findByConcertId(concertId)).thenReturn(null);

        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> concertService.schedule(concertId),
                "Schedule not found");
    }



}