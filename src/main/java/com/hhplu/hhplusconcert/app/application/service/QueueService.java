package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.queue.entity.Queue;
import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import com.hhplu.hhplusconcert.app.infrastructure.queue.QueueJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {

    @Autowired
    QueueJpaRepository queueJpaRepository;

    /**
     * 토큰 생성 및 대기열 저장
     * @param user
     * @return
     */
    public Queue token(User user) {
        Queue queue = new Queue();
        queue.token(user);
        return queueJpaRepository.save(queue);
    }

    public Queue getToken(String queueToken) {
        return queueJpaRepository.findByQueueToken(queueToken).orElseThrow(() -> new IllegalArgumentException("대기열 토큰을 찾을 수 없습니다."));
    }
}
