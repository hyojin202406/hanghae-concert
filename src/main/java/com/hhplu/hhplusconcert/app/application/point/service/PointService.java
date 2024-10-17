package com.hhplu.hhplusconcert.app.application.point.service;

import com.hhplu.hhplusconcert.app.application.point.command.GetPointCommand;
import com.hhplu.hhplusconcert.app.application.point.command.RechargeCommand;
import com.hhplu.hhplusconcert.app.domain.point.entity.Point;
import com.hhplu.hhplusconcert.app.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    public Point point(Long userId) {
        return pointRepository.point(userId);
    }

    public GetPointCommand rechargePoint(RechargeCommand command) {
        Point point = point(command.getUserId());
        BigDecimal pointAmount = command.getPointAmount();
        if (pointAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("충전 금액은 0보다 커야 합니다.");
        }
        point.addPointAmount(pointAmount);
        return new GetPointCommand(point.getPointAmount());
    }

    public GetPointCommand getPoint(Long userId) {
        Point point = point(userId);
        return new GetPointCommand(point.getPointAmount());
    }

    public Point subtractUserPoints(Long userId, BigDecimal totalAmount) {
        Point point = point(userId);
        if (point.getPointAmount().compareTo(totalAmount) < 0) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        point.subtractPointAmount(totalAmount);
        return point;
    }
}
