package com.hhplu.hhplusconcert.app.application.event.reservation;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ReservationSuccessEvent {
    private final String eventKey;          // 고유 식별자
    private final Long reservationId;       // 예약 아이디
    private final Long reservationKey;      // 예약 키
    private final String version;           // 메시지 버전
    private final LocalDateTime createdAt;  // 생성 시간

    public ReservationSuccessEvent(Long reservationId, Long reservationKey) {
        this.eventKey = UUID.randomUUID().toString(); // 동일한 UUID 생성
        this.reservationId = reservationId;
        this.reservationKey = reservationKey;
        this.version = "1.0";
        this.createdAt = LocalDateTime.now();
    }
}