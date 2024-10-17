package com.hhplu.hhplusconcert.app.domain.point.repository;

import com.hhplu.hhplusconcert.app.domain.point.entity.Point;

public interface PointRepository {
    Point point(Long userId);
}
