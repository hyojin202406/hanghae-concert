package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.service.concert.dto.ConcertResponseDto;
import com.hhplu.hhplusconcert.app.application.service.concert.dto.ConcertSeatsResponseDto;
import com.hhplu.hhplusconcert.common.exception.BaseException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ConcertFacadeTest {

    @Autowired
    private ConcertFacade concertFacade;

    @Test
    public void testGetConcertSchedules_Success() {
        // Given
        Long concertId = 1L;

        // When
        ConcertResponseDto response = concertFacade.getConcertSchedules(concertId);

        // Then
        assertNotNull(response);
        assertEquals(concertId, response.getConcertId());
        assertFalse(response.getSchedules().isEmpty());
    }

    @Test
    public void testGetConcertSchedules_ConcertNotFound() {
        // Given
        Long concertId = 999L; // 존재하지 않는 콘서트 ID

        // When & Then
        BaseException exception = assertThrows(BaseException.class, () -> {
            concertFacade.getConcertSchedules(concertId);
        });
        assertEquals("Not found", exception.getMessage());
    }

    @Test
    public void testGetConcertSeats_Success() {
        // Given
        Long concertId = 1L; // 테스트에 사용할 콘서트 ID
        Long scheduleId = 1L; // 테스트에 사용할 스케줄 ID

        // When
        ConcertSeatsResponseDto response = concertFacade.getConcertSeats(concertId, scheduleId);

        // Then
        assertNotNull(response);
        assertEquals(concertId, response.getConcertId());
        assertEquals(scheduleId, response.getScheduleId());
        assertFalse(response.getAllSeats().isEmpty());
        assertFalse(response.getAvailableSeats().isEmpty());
    }

    @Test
    public void testGetConcertSeats_ScheduleNotFound() {
        // Given
        Long concertId = 1L; // 존재하는 콘서트 ID
        Long scheduleId = 999L; // 존재하지 않는 스케줄 ID

        // When & Then
        BaseException exception = assertThrows(BaseException.class, () -> {
            concertFacade.getConcertSeats(concertId, scheduleId);
        });
        assertEquals("Not found", exception.getMessage());
    }
}