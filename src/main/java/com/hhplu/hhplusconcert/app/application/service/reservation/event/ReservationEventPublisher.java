package com.hhplu.hhplusconcert.app.application.service.reservation.event;

import com.hhplu.hhplusconcert.app.domain.reservation.event.dto.ReservationSuccessEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ReservationEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public ReservationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void success(ReservationSuccessEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}