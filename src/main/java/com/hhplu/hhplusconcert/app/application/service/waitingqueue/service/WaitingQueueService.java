package com.hhplu.hhplusconcert.app.application.service.waitingqueue.service;

import com.hhplu.hhplusconcert.app.common.error.ErrorCode;
import com.hhplu.hhplusconcert.app.common.exception.BaseException;
import com.hhplu.hhplusconcert.app.domain.waitingqueue.entity.WaitingQueue;
import com.hhplu.hhplusconcert.app.domain.waitingqueue.repository.WaitingQueueRepository;
import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitingQueueService {

    private final WaitingQueueRepository waitingQueueRepository;

    /**
     * 토큰 생성 및 대기열 저장
     * @param user
     * @return
     */
    public WaitingQueue token(User user) {
        WaitingQueue waitingQueue = new WaitingQueue();
        waitingQueue.token(user);
        return waitingQueueRepository.token(waitingQueue);
    }

    /**
     * 대기열 토큰 조회
     * @return
     */
    public WaitingQueue getToken(String queueToken) {
        return waitingQueueRepository.getToken(queueToken)
                .orElseThrow(() -> new BaseException(ErrorCode.WAITING_QUEUE_NOT_FOUND));
    }

    /**
     * ACTIVE 상태인 마지막 사용자의 ID 조회
     * @return
     */
    public Long getLastActiveId() {
        return waitingQueueRepository.getLastActiveId();
    }
}
