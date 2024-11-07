package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.application.service.concert.service.ScheduleService;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(properties = "spring.config.location=classpath:/application-mysql.yml")
@ActiveProfiles("mysql")  // 필요에 따라 특정 프로파일을 활성화
class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @Test
    void 스케줄_조회_성공() {
        long startTime = System.nanoTime();

        // When
        List<Schedule> schedules = scheduleService.schedule(1L);

        // Then
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("스케줄 조회 경과 시간 (나노초): " + duration);

        Assertions.assertThat(schedules).hasSize(2);
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

        Assertions.assertThat(schedules).hasSize(2);
    }
}