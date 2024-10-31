package com.hhplu.hhplusconcert.app.infrastructure.point;

import com.hhplu.hhplusconcert.app.domain.point.entity.Point;
import com.hhplu.hhplusconcert.app.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointJpaRepository;

    @Override
    public Point point(Long userId) {
        return pointJpaRepository.findByUserIdWithLock(userId);
    }
}
