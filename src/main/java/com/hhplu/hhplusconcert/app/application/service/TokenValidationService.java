package com.hhplu.hhplusconcert.app.application.service;

import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;
import com.hhplu.hhplusconcert.app.domain.watingqueue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenValidationService {

    private final WaitingQueueRepository queueRepository;

    public boolean isValidToken(String token) {
        WaitingQueue waitingQueue = queueRepository.getToken(token);
        return waitingQueue != null;
    }

}