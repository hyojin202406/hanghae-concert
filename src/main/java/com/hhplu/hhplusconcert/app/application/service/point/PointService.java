package com.hhplu.hhplusconcert.app.application.service.point;

import com.hhplu.hhplusconcert.app.application.service.point.dto.GetPointDto;
import com.hhplu.hhplusconcert.app.application.service.point.dto.RechargeDto;
import com.hhplu.hhplusconcert.app.domain.point.entity.Point;
import com.hhplu.hhplusconcert.app.domain.point.repository.PointRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public GetPointDto rechargePoint(RechargeDto command) {
        Point point = point(command.getUserId());
        point.recharge(command.getPointAmount());
        log.info("recharge point: {}", point.getPointAmount());
        return new GetPointDto(point.getPointAmount());
    }

    public GetPointDto getPoint(Long userId) {
        Point point = point(userId);
        return new GetPointDto(point.getPointAmount());
    }

    public Point subtractUserPoints(Long userId, BigDecimal totalAmount) {
        Point point = point(userId);
        point.subtractPointAmount(point.getPointAmount(), totalAmount);
        return point;
    }

}
