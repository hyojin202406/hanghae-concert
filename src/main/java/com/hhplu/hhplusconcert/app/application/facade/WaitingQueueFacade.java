package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.service.user.service.UserService;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.command.CreateWaitingQueueCommand;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.command.GetWaitingQueueCommand;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.service.WaitingQueueRedisService;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.service.WaitingQueueService;
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

    public CreateWaitingQueueCommand token(Long userId) {
        User user = userService.user(userId);
        String token = waitingQueueRedisService.createWaitingQueueToken(user);
        return new CreateWaitingQueueCommand(token, LocalDateTime.now());
    }

    public GetWaitingQueueCommand queue(String queueToken) {
        int waitingQueuePosition = waitingQueueRedisService.getUserPosition(queueToken);
        return new GetWaitingQueueCommand(waitingQueuePosition);
    }
}
