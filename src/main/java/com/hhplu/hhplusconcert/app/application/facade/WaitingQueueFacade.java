package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.service.UserService;
import com.hhplu.hhplusconcert.app.application.service.WaitingQueueService;
import com.hhplu.hhplusconcert.app.application.waitingqueue.CreateWaitingQueueCommand;
import com.hhplu.hhplusconcert.app.application.waitingqueue.GetWaitingQueueCommand;
import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitingQueueFacade {

    private final WaitingQueueService waitingQueueService;
    private final UserService userService;

    public CreateWaitingQueueCommand token(Long userId) {
        return new CreateWaitingQueueCommand(waitingQueueService.token(userService.user(userId)));
    }

    public GetWaitingQueueCommand queue(String queueToken) {
        WaitingQueue waitingQueue = waitingQueueService.getToken(queueToken);
        Long lastActiveId = waitingQueueService.getLastActiveId();
        return new GetWaitingQueueCommand(waitingQueue, lastActiveId);
    }
}
