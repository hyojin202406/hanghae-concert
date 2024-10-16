package com.hhplu.hhplusconcert.app.domain.reservation;

public enum ReservationStatus {
    TEMPORARY_RESERVED, // 임시 예약 상태
    CONFIRMED,          // 예약 완료 상태
    FAILED              // 예약 실패 상태
}