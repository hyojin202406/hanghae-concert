package com.hhplu.hhplusconcert.application;

import com.hhplu.hhplusconcert.domain.queue.QueueEntity;
import com.hhplu.hhplusconcert.domain.user.UserEntity;
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
    public QueueEntity token(UserEntity user) {
        QueueEntity queue = new QueueEntity();
        queue.token(user);
        return queueRepository.save(queue);
    }
}
