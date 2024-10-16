package com.hhplu.hhplusconcert.app.interfaces.api.payment;

import com.hhplu.hhplusconcert.app.interfaces.api.payment.res.PaymentHistoryItem;
import com.hhplu.hhplusconcert.app.interfaces.api.payment.res.PaymentHistoryResponse;
import com.hhplu.hhplusconcert.app.interfaces.api.payment.res.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    /**
     * 결제
     * @param userId
     * @return
     */
    @PostMapping("/users/{userId}")
    public ResponseEntity<PaymentResponse> pay(@PathVariable("userId") Long userId) {
        PaymentResponse response = PaymentResponse.builder()
                .paymentId(1L)
                .amount(25000L)
                .paymentStatus("PAYMENT_SUCCESS")
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * 결제 내역 조회
     * @param userId
     * @return
     */
    @PostMapping("/history/users/{userId}")
    public ResponseEntity<PaymentHistoryResponse> getPayments(@PathVariable("userId") Long userId) {
        List<PaymentHistoryItem> paymentHistories = List.of(
                PaymentHistoryItem.builder().paymentId(1L).amount(BigDecimal.valueOf(30000)).paymentAt(LocalDateTime.of(2024,10,8,10,0,0)).build(),
                PaymentHistoryItem.builder().paymentId(2L).amount(BigDecimal.valueOf(30000)).paymentAt(LocalDateTime.of(2024,10,9,10,0,0)).build()
        );

        PaymentHistoryResponse response = PaymentHistoryResponse.builder()
                .userId(userId)
                .payments(paymentHistories)
                .build();

        return ResponseEntity.ok(response);
    }
}
