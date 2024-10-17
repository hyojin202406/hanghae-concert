package com.hhplu.hhplusconcert.app.interfaces.api.payment;

import com.hhplu.hhplusconcert.app.interfaces.api.payment.res.PaymentHistoryItem;
import com.hhplu.hhplusconcert.app.interfaces.api.payment.res.PaymentHistoryResponse;
import com.hhplu.hhplusconcert.app.interfaces.api.payment.res.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "결제", description = "사용자가 결제를 진행합니다.")
    @ApiResponse(responseCode = "200", description = "결제 성공")
    @PostMapping("/users/{userId}")
    public ResponseEntity<PaymentResponse> pay(
            @Parameter(description = "사용자 ID") @PathVariable("userId") Long userId,
            @Parameter(description = "사용자 인증 토큰", required = true) @RequestHeader("QUEUE-TOKEN") String queueToken
    ) {
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
    @Operation(summary = "결제 내역 조회", description = "사용자의 결제 내역을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "결제 내역 조회 성공")
    @PostMapping("/history/users/{userId}")
    public ResponseEntity<PaymentHistoryResponse> getPayments(
            @Parameter(description = "사용자 ID") @PathVariable("userId") Long userId,
            @Parameter(description = "사용자 인증 토큰", required = true) @RequestHeader("QUEUE-TOKEN") String queueToken) {
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
