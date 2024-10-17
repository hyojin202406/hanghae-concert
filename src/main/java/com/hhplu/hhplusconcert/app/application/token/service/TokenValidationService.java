package com.hhplu.hhplusconcert.app.application.token.service;

import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;
import com.hhplu.hhplusconcert.app.domain.watingqueue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenValidationService {

    private final WaitingQueueRepository queueRepository;

    public boolean isValidToken(String token) {
        Optional<WaitingQueue> waitingQueue = queueRepository.isValidToken(token);
        return waitingQueue.isPresent();
    }

}