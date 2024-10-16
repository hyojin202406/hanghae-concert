package com.hhplu.hhplusconcert.app.interfaces.api.reservation;

import com.hhplu.hhplusconcert.app.interfaces.api.concert.dto.SeatValue;
import com.hhplu.hhplusconcert.app.interfaces.api.reservation.req.ReservationRequest;
import com.hhplu.hhplusconcert.app.interfaces.api.reservation.res.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(@RequestBody ReservationRequest request) {
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
