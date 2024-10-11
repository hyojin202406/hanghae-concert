package com.hhplu.hhplusconcert.interfaces.api.payment;

import com.hhplu.hhplusconcert.interfaces.api.payment.req.PointRequest;
import com.hhplu.hhplusconcert.interfaces.api.payment.res.PaymentResponse;
import com.hhplu.hhplusconcert.interfaces.api.payment.res.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * 잔액 충전
     * @param userId
     * @param request
     * @return
     */
    @PostMapping("/point/users/{userId}/recharge")
    public ResponseEntity<PointResponse> recharge(@PathVariable("userId") Long userId,
                                                  @RequestBody PointRequest request) {
        PointResponse response = PointResponse.builder()
                .userId(1L)
                .currentPointAmount(40000L)
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * 잔액 조회
     * @param userId
     * @return
     */
    @PostMapping("/point/users/{userId}")
    public ResponseEntity<PointResponse> point(@PathVariable("userId") Long userId) {
        PointResponse response = PointResponse.builder()
                .userId(1L)
                .currentPointAmount(40000L)
                .build();
        return ResponseEntity.ok(response);
    }
}
