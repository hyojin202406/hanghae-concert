package com.hhplu.hhplusconcert.app.domain.concert.repository;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Concert;

public interface ConcertRepository {
    Concert existsConcert(Long concertId);
}
