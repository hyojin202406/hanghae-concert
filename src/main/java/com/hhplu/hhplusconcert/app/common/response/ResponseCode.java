package com.hhplu.hhplusconcert.app.common.response;

import org.springframework.http.HttpStatus;

public enum ResponseCode {

    COMMON_SUCCESS(HttpStatus.OK, "RES_200", "요청이 성공적으로 처리되었습니다.", "Request was successful."),
    COMMON_CREATED(HttpStatus.CREATED, "RES_201", "리소스가 성공적으로 생성되었습니다.", "Resource created successfully."),
    COMMON_NO_CONTENT(HttpStatus.NO_CONTENT, "RES_204", "리소스가 성공적으로 삭제되었습니다.", "Resource deleted successfully."),

    CONCERT_SCHEDULE_RETRIEVED(HttpStatus.OK, "RES_CS01", "콘서트 일정이 성공적으로 조회되었습니다.", "Concert schedule retrieved successfully."),

    POINT_RECHARGE_SUCCESS(HttpStatus.OK, "RES_PS01", "잔액이 성공적으로 충전되었습니다.", "Balance recharged successfully."),

    PAYMENT_SUCCESS(HttpStatus.OK, "RES_PAY01", "결제가 성공적으로 처리되었습니다.", "Payment processed successfully."),

    USER_RETRIEVED(HttpStatus.OK, "RES_US01", "유저 정보가 성공적으로 조회되었습니다.", "User information retrieved successfully."),

    WAITING_QUEUE_JOINED(HttpStatus.OK, "RES_WQJ01", "대기열에 성공적으로 추가되었습니다.", "Successfully joined the queue."),

    SEAT_RESERVED(HttpStatus.CREATED, "RES_SR01", "좌석이 성공적으로 예약되었습니다.", "Seat reserved successfully.");

    private final HttpStatus httpStatus;
    private final String responseCode;
    private final String internalMessage;
    private final String externalMessage;

    ResponseCode(HttpStatus httpStatus, String responseCode, String internalMessage, String externalMessage) {
        this.httpStatus = httpStatus;
        this.responseCode = responseCode;
        this.internalMessage = internalMessage;
        this.externalMessage = externalMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getInternalMessage() {
        return internalMessage;
    }

    public String getExternalMessage() {
        return externalMessage;
    }
}
