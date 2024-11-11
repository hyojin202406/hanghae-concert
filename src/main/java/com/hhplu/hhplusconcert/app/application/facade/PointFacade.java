package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.service.point.dto.GetPointDto;
import com.hhplu.hhplusconcert.app.application.service.point.dto.RechargeDto;
import com.hhplu.hhplusconcert.app.application.service.point.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointFacade {
    private final PointService pointService;

    public GetPointDto rechargePoint(RechargeDto command) {
       return pointService.rechargePoint(command);
    }
}
