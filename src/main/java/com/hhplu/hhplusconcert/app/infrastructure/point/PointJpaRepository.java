package com.hhplu.hhplusconcert.app.infrastructure.point;

import com.hhplu.hhplusconcert.app.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointJpaRepository extends JpaRepository<Point, Long> {
    Point findByUserId(Long userId);
}
