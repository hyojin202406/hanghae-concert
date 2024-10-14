package com.hhplu.hhplusconcert.infrastructure.concert;

import com.hhplu.hhplusconcert.domain.concert.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByConcertId(Long concertId);
}
