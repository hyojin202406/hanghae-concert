package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.service.user.service.UserService;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.service.WaitingQueueService;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.command.CreateWaitingQueueCommand;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.command.GetWaitingQueueCommand;
import com.hhplu.hhplusconcert.app.common.error.ErrorCode;
import com.hhplu.hhplusconcert.app.common.exception.BaseException;
import com.hhplu.hhplusconcert.app.domain.waitingqueue.entity.WaitingQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaitingQueueFacade {

    private final WaitingQueueService waitingQueueService;
    private final UserService userService;

    public CreateWaitingQueueCommand token(Long userId) {
        WaitingQueue waitingQueue = waitingQueueService.token(userService.user(userId));
        return new CreateWaitingQueueCommand(waitingQueue.getQueueToken(), waitingQueue.getIssuedAt());
    }

    public GetWaitingQueueCommand queue(String queueToken) {
        try {
            WaitingQueue waitingQueue = waitingQueueService.getToken(queueToken);
            Long lastActiveId = waitingQueueService.getLastActiveId();
            return new GetWaitingQueueCommand(waitingQueue.getId(), waitingQueue.getUserId(), waitingQueue.getQueueStatus(), waitingQueue.getIssuedAt(), lastActiveId);
        } catch (BaseException e) {
            ErrorCode errorCode = e.getErrorCode();
            log.error(errorCode.getInternalMessage());
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
