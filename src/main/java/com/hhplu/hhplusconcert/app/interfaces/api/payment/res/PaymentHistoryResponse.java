package com.hhplu.hhplusconcert.app.interfaces.api.payment.res;

import com.hhplu.hhplusconcert.app.domain.payment.entity.PaymentHistory;
import com.hhplu.hhplusconcert.app.interfaces.api.concert.dto.SeatItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PaymentHistoryResponse {
    private Long userId; // userId 추가
    private List<PaymentHistoryItem> payments; // 결제 내역 리스트 추가

    public PaymentHistoryResponse(Long userId, List<PaymentHistory> paymentHistories) {
        List<PaymentHistoryItem> list = paymentHistories.stream().map(paymentHistory -> new PaymentHistoryItem(
                paymentHistory.getPaymentId(),
                paymentHistory.getAmount(),
                paymentHistory.getPaymentStatus().toString(),
                paymentHistory.getPaymentAt()
        )).toList();

        this.userId = userId;
        this.payments = list;
    }
}
