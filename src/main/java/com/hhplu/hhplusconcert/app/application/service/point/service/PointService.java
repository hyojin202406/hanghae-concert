package com.hhplu.hhplusconcert.app.application.service.point.service;

import com.hhplu.hhplusconcert.app.application.service.point.command.GetPointCommand;
import com.hhplu.hhplusconcert.app.application.service.point.command.RechargeCommand;
import com.hhplu.hhplusconcert.app.domain.point.entity.Point;
import com.hhplu.hhplusconcert.app.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    public Point point(Long userId) {
        return pointRepository.point(userId);
    }


    public GetPointCommand rechargePoint(RechargeCommand command) {
        Point point = point(command.getUserId());
        point.recharge(command.getPointAmount());
        return new GetPointCommand(point.getPointAmount());
    }

    public GetPointCommand getPoint(Long userId) {
        Point point = point(userId);
        return new GetPointCommand(point.getPointAmount());
    }

    public Point subtractUserPoints(Long userId, BigDecimal totalAmount) {
        Point point = point(userId);
        point.subtractPointAmount(point.getPointAmount(), totalAmount);
        return point;
    }

}
