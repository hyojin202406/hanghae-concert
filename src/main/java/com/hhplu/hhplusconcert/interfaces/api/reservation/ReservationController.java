package com.hhplu.hhplusconcert.interfaces.api.reservation;

import com.hhplu.hhplusconcert.interfaces.api.concert.dto.Seat;
import com.hhplu.hhplusconcert.interfaces.api.reservation.req.PointRequest;
import com.hhplu.hhplusconcert.interfaces.api.reservation.req.ReservationRequest;
import com.hhplu.hhplusconcert.interfaces.api.reservation.res.PaymentResponse;
import com.hhplu.hhplusconcert.interfaces.api.reservation.res.PointResponse;
import com.hhplu.hhplusconcert.interfaces.api.reservation.res.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    /**
     * 좌석 예약
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(@RequestBody ReservationRequest request) {
        List<Seat> seats = List.of(
                Seat.builder().seatNumber(10).seatPrice(10000).build(),
                Seat.builder().seatNumber(11).seatPrice(15000).build()
        );

        int totalPrice = seats.stream().mapToInt(Seat::getSeatPrice).sum();

        ReservationResponse response = ReservationResponse.builder()
                .reservationId(1L)
                .concertId(1L)
                .concertName("콘서트")
                .concertAt(LocalDateTime.of(2024, 10, 8, 10, 0))
                .seats(seats)
                .totalPrice(totalPrice)
                .reservationStatus("PAYMENT_PENDING")
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 결제
     * @param userId
     * @return
     */
    @PostMapping("/payments/users/{userId}")
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
                .currentAmount(40000L)
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
                .currentAmount(40000L)
                .build();
        return ResponseEntity.ok(response);
    }
}
