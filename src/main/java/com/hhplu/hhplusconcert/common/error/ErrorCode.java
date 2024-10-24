package com.hhplu.hhplusconcert.common.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // Common Errors
    COMMON_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_CA01", "찾을 수 없습니다.", "Invalid request"),
    COMMON_BAD_REQUEST(HttpStatus.BAD_REQUEST, "ERR_CA02", "잘못된 요청입니다.", "Invalid request"),
    COMMON_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ERR_CA03", "서버에 문제가 발생했습니다.", "Invalid request"),

    // Concert Errors
    CONCERT_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_C01", "존재하지 않는 콘서트입니다.", "Not found"),
    CONCERT_SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_CS02", "콘서트 일정이 존재하지 않습니다.", "Not found"),

    // Point Errors
    POINT_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_POI01", "해당 잔액 정보를 찾을 수 없습니다.", "Not found"),
    POINT_BAD_RECHARGE_REQUEST(HttpStatus.BAD_REQUEST, "ERR_POI02", "잔액이 부족합니다.", "Insufficient balance"),
    POINT_INVALID_RECHARGE_AMOUNT(HttpStatus.BAD_REQUEST, "ERR_POI03", "충전 금액은 0보다 커야 합니다.", "Invalid request"),
    POINT_INVALID_ADD_AMOUNT(HttpStatus.BAD_REQUEST, "ERR_POI04", "추가할 금액은 0 이상이어야 합니다.", "Invalid request"),
    POINT_INVALID_DEDUCT_AMOUNT(HttpStatus.BAD_REQUEST, "ERR_POI05", "차감할 금액은 0 이상이어야 합니다.", "Invalid request"),

    // Payment Errors
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_PAY01", "결제 정보를 찾을 수 없습니다.", "Not found"),
    PAYMENT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "ERR_PAY02", "잘못된 결제 정보 요청입니다.", "Invalid request"),

    // Payment History Errors
    PAYMENT_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_PAY_HIST01", "결제 내역을 찾을 수 없습니다.", "Not found"),

    // User Errors
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_US01", "해당 유저를 찾을 수 없습니다.", "Not found"),

    // WaitingQueue Errors
    WAITING_QUEUE_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_WQ01", "해당 대기열을 찾을 수 없습니다.", "Not found"),
    WAITING_QUEUE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "ERR_WQ02", "해당 대기열은 허용되지 않습니다.", "Invalid request"),

    // Seat Errors
    SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_SE01", "잘못된 좌석 정보가 포함되어 있습니다.", "Invalid request"),
    SEAT_RESERVATION_FAILED(HttpStatus.BAD_REQUEST, "ERR_SE02", "좌석 예약에 실패했습니다. 다시 시도해 주세요.", "Seat reservation failed"),
    SEAT_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "ERR_SE03", "예약할 수 없는 좌석이 포함되어 있습니다.", "Invalid request");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String internalMessage;
    private final String externalMessage;

    ErrorCode(HttpStatus httpStatus, String errorCode, String internalMessage, String externalMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.internalMessage = internalMessage;
        this.externalMessage = externalMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getInternalMessage() {
        return internalMessage;
    }

    public String getExternalMessage() {
        return externalMessage;
    }
}
