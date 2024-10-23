package com.hhplu.hhplusconcert.app.infrastructure.concert;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Concert;
import com.hhplu.hhplusconcert.app.domain.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {
    private final ConcertJpaRepository concertJpaRepository;
    @Override
    public Optional<Concert> existsConcert(Long concertId) {
        return concertJpaRepository.findById(concertId);
    }
}
