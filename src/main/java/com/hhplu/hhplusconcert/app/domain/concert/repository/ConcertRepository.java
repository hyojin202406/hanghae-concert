package com.hhplu.hhplusconcert.app.domain.concert.repository;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Concert;

import java.util.Optional;

public interface ConcertRepository {
    Optional<Concert> existsConcert(Long concertId);
}
