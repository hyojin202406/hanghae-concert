package com.hhplu.hhplusconcert.app.application.event.reservation;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ReservationSuccessEvent {
    private final String eventKey;
    private final Long reservationId;
    private final Long reservationKey;

    public ReservationSuccessEvent(Long reservationId, Long reservationKey) {
        this.eventKey = UUID.randomUUID().toString(); // 동일한 UUID 생성
        this.reservationId = reservationId;
        this.reservationKey = reservationKey;
    }
}