package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.application.service.concert.service.ScheduleService;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import com.hhplu.hhplusconcert.app.domain.concert.repository.ScheduleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

//@SpringBootTest(properties = "spring.config.location=classpath:/application-mysql.yml")
//@ActiveProfiles("mysql")  // 필요에 따라 특정 프로파일을 활성화
@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    void saveCache() {
        Long concertId = 1L;
        List<Schedule> mockSchedules = Arrays.asList(
                new Schedule(1L, concertId, null, null),
                new Schedule(2L, concertId, null, null)
        );

        // 스케줄 조회 시 mockSchedules 반환
        when(scheduleRepository.existsSchedule(concertId)).thenReturn(mockSchedules);

        // When: 첫 번째 호출 -> DB에서 데이터 조회
        scheduleService.redisCacheableSchedule(concertId);
    }

    @AfterEach
    void clearCache() {
        // 특정 캐시 키 삭제
        redisTemplate.delete("concertSchedules::1");
    }


    @Test
    void 스케줄_조회_성공() {
        long startTime = System.nanoTime();

        // When
        List<Schedule> schedules = scheduleService.schedule(1L);

        // Then
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("스케줄 조회 경과 시간 (나노초): " + duration);

        assertThat(schedules).hasSize(2);
    }

    @Test
    void 레디스_캐싱_스케줄_조회_성공() {
        Long concertId = 1L;

        long startTime = System.nanoTime();

        // When
        List<Schedule> schedules = scheduleService.redisCacheableSchedule(concertId);

        // Then
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("레디스 캐싱 스케줄 조회 경과 시간 (나노초): " + duration);

        assertThat(schedules).hasSize(2);
    }

    @Test
    void 레디스_캐싱_스케줄_조회_검증_성공() {
        // Given
        Long concertId = 1L;

        // When: 두 번째 호출 -> 캐시에서 데이터 반환
        List<Schedule> schedules = scheduleService.redisCacheableSchedule(concertId);

        // Then: 두 번째 호출의 결과를 검증
        assertThat(schedules).hasSize(2);
        verify(scheduleRepository, times(0)).existsSchedule(concertId);
    }

}