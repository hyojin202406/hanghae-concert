package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.service.user.UserService;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.dto.CreateWaitingQueueDto;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.dto.GetWaitingQueueDto;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.WaitingQueueRedisService;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.WaitingQueueService;
import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaitingQueueFacade {

    private final WaitingQueueService waitingQueueService;
    private final WaitingQueueRedisService waitingQueueRedisService;
    private final UserService userService;

    public CreateWaitingQueueDto token(Long userId) {
        User user = userService.user(userId);
        String token = waitingQueueRedisService.createWaitingQueueToken(user);
        return new CreateWaitingQueueDto(token, LocalDateTime.now());
    }

    public GetWaitingQueueDto queue(String queueToken) {
        int waitingQueuePosition = waitingQueueRedisService.getUserPosition(queueToken);
        return new GetWaitingQueueDto(waitingQueuePosition);
    }
}
