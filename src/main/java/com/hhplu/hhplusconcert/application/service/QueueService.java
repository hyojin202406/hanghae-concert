package com.hhplu.hhplusconcert.application.service;

import com.hhplu.hhplusconcert.domain.queue.Queue;
import com.hhplu.hhplusconcert.domain.user.User;
import com.hhplu.hhplusconcert.infrastructure.queue.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {

    @Autowired
    QueueRepository queueRepository;

    /**
     * 토큰 생성 및 대기열 저장
     * @param user
     * @return
     */
    public Queue token(User user) {
        Queue queue = new Queue();
        queue.token(user);
        return queueRepository.save(queue);
    }

    public Queue getToken(String queueToken) {
        return queueRepository.findByQueueToken(queueToken).orElseThrow(() -> new IllegalArgumentException("Queue token not found"));
    }
}
