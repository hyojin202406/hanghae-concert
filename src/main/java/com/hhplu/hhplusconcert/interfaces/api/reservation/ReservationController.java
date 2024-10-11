package com.hhplu.hhplusconcert.interfaces.api.reservation;

import com.hhplu.hhplusconcert.interfaces.api.concert.dto.Seat;
import com.hhplu.hhplusconcert.interfaces.api.reservation.req.ReservationRequest;
import com.hhplu.hhplusconcert.interfaces.api.reservation.res.ReservationResponse;
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

}
