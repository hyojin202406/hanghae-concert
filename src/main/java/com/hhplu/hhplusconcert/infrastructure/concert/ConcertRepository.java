package com.hhplu.hhplusconcert.infrastructure.concert;

import com.hhplu.hhplusconcert.domain.concert.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
}
