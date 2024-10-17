package com.hhplu.hhplusconcert.app.interfaces.api.reservation;

import com.hhplu.hhplusconcert.app.interfaces.api.concert.dto.SeatValue;
import com.hhplu.hhplusconcert.app.interfaces.api.reservation.req.ReservationRequest;
import com.hhplu.hhplusconcert.app.interfaces.api.reservation.res.ReservationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    /**
     * 좌석 예약
     * @param request
     * @return
     */
    @Operation(summary = "좌석 예약", description = "사용자가 좌석을 예약합니다.")
    @ApiResponse(responseCode = "200", description = "예약 성공")
    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(
            @Parameter(description = "예약 요청 정보") @RequestBody ReservationRequest request,
            @Parameter(description = "사용자 인증 토큰", required = true) @RequestHeader("QUEUE-TOKEN") String queueToken
    ) {
        List<SeatValue> seats = List.of(
                SeatValue.builder().seatNumber(10).seatPrice(10000).build(),
                SeatValue.builder().seatNumber(11).seatPrice(15000).build()
        );

        int totalPrice = seats.stream().mapToInt(SeatValue::getSeatPrice).sum();

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

}
