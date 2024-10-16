package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;
import com.hhplu.hhplusconcert.app.domain.watingqueue.repository.WaitingQueueRepository;
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
        WaitingQueue queue = new WaitingQueue();
        queue.token(user);
        return waitingQueueRepository.token(queue);
    }

    /**
     * 대기열 토큰 조회
     * @return
     */
    public WaitingQueue getToken(String queueToken) {
        return waitingQueueRepository.getToken(queueToken);
    }

    /**
     * ACTIVE 상태인 마지막 사용자의 ID 조회
     * @return
     */
    public Long getLastActiveId() {
        return waitingQueueRepository.getLastActiveId();
    }
}
