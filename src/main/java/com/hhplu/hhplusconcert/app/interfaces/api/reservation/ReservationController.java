package com.hhplu.hhplusconcert.app.interfaces.api.reservation;

import com.hhplu.hhplusconcert.app.application.facade.ReservationFacade;
import com.hhplu.hhplusconcert.app.application.service.reservation.dto.ReserveSeatsResponseDto;
import com.hhplu.hhplusconcert.app.interfaces.api.reservation.req.ReservationRequest;
import com.hhplu.hhplusconcert.app.interfaces.api.reservation.res.ReservationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationFacade reservationFacade;

    @Operation(summary = "좌석 예약", description = "사용자가 좌석을 예약합니다.")
    @ApiResponse(responseCode = "200", description = "예약 성공")
    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(
            @Parameter(description = "예약 요청 정보") @RequestBody ReservationRequest request,
            @Parameter(description = "사용자 인증 토큰", required = true) @RequestHeader("QUEUE-TOKEN") String queueToken
    ) {
        ReserveSeatsResponseDto command = reservationFacade.reserveSeats(request.toReserveSeatsCommand());
        return ResponseEntity.ok(ReservationResponse.from(command));
    }

}
