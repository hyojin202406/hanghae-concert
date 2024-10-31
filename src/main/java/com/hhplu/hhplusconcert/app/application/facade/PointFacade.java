package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.service.point.command.GetPointCommand;
import com.hhplu.hhplusconcert.app.application.service.point.command.RechargeCommand;
import com.hhplu.hhplusconcert.app.application.service.point.service.PointService;
import com.hhplu.hhplusconcert.common.annotation.RedissonLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointFacade {
    private final PointService pointService;

    @RedissonLock(value = "#command.getUserId()")
    public GetPointCommand rechargePoint(RechargeCommand command) {
       return pointService.rechargePoint(command);
    }
}
