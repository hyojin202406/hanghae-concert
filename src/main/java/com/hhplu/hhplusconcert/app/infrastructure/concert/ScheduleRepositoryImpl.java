package com.hhplu.hhplusconcert.app.infrastructure.concert;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Schedule;
import com.hhplu.hhplusconcert.app.domain.concert.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository {
    private final ScheduleJpaRepository scheduleJpaRepository;

    @Override
    public Optional<List<Schedule>> getSchedulesByConcertId(Long concertId) {
        return scheduleJpaRepository.findByConcertId(concertId);
    }

    @Override
    public Optional<Schedule> getSchedulesByScheduleId(Long scheduleId) {
        return scheduleJpaRepository.findById(scheduleId);
    }
}
