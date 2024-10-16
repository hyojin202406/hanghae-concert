package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.queue.entity.WaitingQueue;
import com.hhplu.hhplusconcert.app.domain.queue.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenValidationService {

    private final QueueRepository queueRepository;

    public boolean isValidToken(String token) {
        WaitingQueue waitingQueue = queueRepository.getToken(token);
        return waitingQueue != null;
    }

}