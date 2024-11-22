package com.hhplu.hhplusconcert.app.domain.reservation;

import com.hhplu.hhplusconcert.app.application.event.reservation.ReservationSuccessEvent;

public interface ReservationMessageProducer {
    void send(String topic, String eventKey, ReservationSuccessEvent Message);
}
