package com.hhplu.hhplusconcert.app.interfaces.api.payment;

import com.hhplu.hhplusconcert.app.application.facade.PaymentFacade;
import com.hhplu.hhplusconcert.app.application.service.payment.dto.GetPaymentsHistoryResponseDto;
import com.hhplu.hhplusconcert.app.application.service.payment.dto.PaymentRequestDto;
import com.hhplu.hhplusconcert.app.application.service.payment.dto.PaymentResponseDto;
import com.hhplu.hhplusconcert.app.interfaces.api.payment.res.PaymentHistoryResponse;
import com.hhplu.hhplusconcert.app.interfaces.api.payment.res.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @Operation(summary = "결제", description = "사용자가 결제를 진행합니다.")
    @ApiResponse(responseCode = "200", description = "결제 성공")
    @PostMapping("{paymentId}/users/{userId}")
    public ResponseEntity<PaymentResponse> pay(
            @Parameter(description = "사용자 ID") @PathVariable("paymentId") Long paymentId,
            @Parameter(description = "사용자 ID") @PathVariable("userId") Long userId,
            @Parameter(description = "사용자 인증 토큰", required = true) @RequestHeader("QUEUE-TOKEN") String queueToken
    ) {
        PaymentResponseDto command = paymentFacade.pay(new PaymentRequestDto(userId, paymentId, queueToken));
        return ResponseEntity.ok(new PaymentResponse(command.getPaymentId(), command.getAmount(), command.getPaymentStatus()));
    }

    @Operation(summary = "결제 내역 조회", description = "사용자의 결제 내역을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "결제 내역 조회 성공")
    @PostMapping("/history/users/{userId}")
    public ResponseEntity<PaymentHistoryResponse> getPayments(
            @Parameter(description = "사용자 ID") @PathVariable("userId") Long userId,
            @Parameter(description = "사용자 인증 토큰", required = true) @RequestHeader("QUEUE-TOKEN") String queueToken) {
        GetPaymentsHistoryResponseDto command = paymentFacade.getPayments(userId);
        return ResponseEntity.ok(new PaymentHistoryResponse(command.getUserId(), command.getPayments()));
    }
}
