package com.hhplu.hhplusconcert.app.interfaces.api.concert;

import com.hhplu.hhplusconcert.app.application.facade.ConcertFacade;
import com.hhplu.hhplusconcert.app.application.service.concert.dto.ConcertResponseDto;
import com.hhplu.hhplusconcert.app.application.service.concert.dto.ConcertSeatsResponseDto;
import com.hhplu.hhplusconcert.app.interfaces.api.concert.res.ScheduleResponse;
import com.hhplu.hhplusconcert.app.interfaces.api.concert.res.SeatResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertFacade concertFacade;

    @Operation(summary = "콘서트 일정 조회", description = "주어진 콘서트 ID에 대한 일정 정보를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "일정 조회 성공")
    @GetMapping("/{concertId}/schedules")
    public ResponseEntity<ScheduleResponse> concertSchedules(
            @Parameter(description = "콘서트 ID") @PathVariable(name = "concertId") Long concertId,
            @Parameter(description = "사용자 인증 토큰", required = true) @RequestHeader("QUEUE-TOKEN") String queueToken) {
        ConcertResponseDto command = concertFacade.getConcertSchedules(concertId);
        return ResponseEntity.ok(ScheduleResponse.from(command));
    }

    @Operation(summary = "콘서트 좌석 조회", description = "주어진 콘서트 ID와 일정 ID에 대한 좌석 정보를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "좌석 조회 성공")
    @GetMapping("/{concertId}/schedules/{scheduleId}/seats")
    public ResponseEntity<SeatResponse> concertSeats(
            @Parameter(description = "콘서트 ID") @PathVariable(name = "concertId") Long concertId,
            @Parameter(description = "일정 ID") @PathVariable(name = "scheduleId") Long scheduleId,
            @Parameter(description = "사용자 인증 토큰", required = true) @RequestHeader("QUEUE-TOKEN") String queueToken) {
        ConcertSeatsResponseDto command = concertFacade.getConcertSeats(concertId, scheduleId);
        return ResponseEntity.ok(SeatResponse.from(command));
    }
}
