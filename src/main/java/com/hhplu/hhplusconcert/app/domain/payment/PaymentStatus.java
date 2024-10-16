package com.hhplu.hhplusconcert.app.domain.payment;

public enum PaymentStatus {
    PENDING,        // 결제 준비 중
    COMPLETED,      // 결제 완료
    FAILED,         // 결제 실패
    CANCELED;       // 결제 취소
}
