package com.hhplu.hhplusconcert.app.interfaces.api.payment.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PaymentHistoryResponse {
    private Long userId; // userId 추가
    private List<PaymentHistoryItem> payments; // 결제 내역 리스트 추가
}
