package com.hhplu.hhplusconcert.app.domain.reservation.event.dto;

import lombok.Getter;

@Getter
public class ReservationSuccessEvent {
    private final Long reservationId;
    private final Long reservationKey;

    public ReservationSuccessEvent(Long reservationId, Long reservationKey) {
        this.reservationId = reservationId;
        this.reservationKey = reservationKey;
    }
}