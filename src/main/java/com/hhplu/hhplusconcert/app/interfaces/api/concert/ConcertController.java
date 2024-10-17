package com.hhplu.hhplusconcert.app.interfaces.api.concert;

import com.hhplu.hhplusconcert.app.interfaces.api.concert.dto.SeatValue;
import com.hhplu.hhplusconcert.app.interfaces.api.concert.res.ScheduleResponse;
import com.hhplu.hhplusconcert.app.interfaces.api.concert.res.SeatResponse;
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
@RequestMapping("/api/concerts")
public class ConcertController {

    /**
     * 콘서트 일정 조회
     * @param concertId
     * @return
     */
    @Operation(summary = "콘서트 일정 조회", description = "주어진 콘서트 ID에 대한 일정 정보를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "일정 조회 성공")
    @GetMapping("/{concertId}/schedules")
    public ResponseEntity<ScheduleResponse> concertSchedule(
            @Parameter(description = "콘서트 ID") @PathVariable Long concertId,
            @Parameter(description = "사용자 인증 토큰", required = true) @RequestHeader("QUEUE-TOKEN") String queueToken) {
        ScheduleResponse response = ScheduleResponse.builder()
                .concertId(concertId) // concertId 추가
                .events(List.of( // events 리스트 추가
                        ScheduleResponse.EventResponse.builder()
                                .scheduleId(1L)
                                .concertAt(LocalDateTime.parse("2024-10-08T10:00:00"))
                                .build(),
                        ScheduleResponse.EventResponse.builder()
                                .scheduleId(2L)
                                .concertAt(LocalDateTime.parse("2024-10-08T12:00:00"))
                                .build()
                ))
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 콘서트 좌석 조회
     * @param concertId
     * @param scheduleId
     * @return
     */
    @Operation(summary = "콘서트 좌석 조회", description = "주어진 콘서트 ID와 일정 ID에 대한 좌석 정보를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "좌석 조회 성공")
    @GetMapping("/{concertId}/schedules/{scheduleId}/seats")
    public ResponseEntity<SeatResponse> concertSchedule(
            @Parameter(description = "콘서트 ID") @PathVariable Long concertId,
            @Parameter(description = "일정 ID") @PathVariable Long scheduleId,
            @Parameter(description = "사용자 인증 토큰", required = true) @RequestHeader("QUEUE-TOKEN") String queueToken) {
        List<SeatValue> allSeats = List.of(
                SeatValue.builder().seatId(1L).seatNumber(1).seatStatus("AVAILABLE").seatPrice(50000).build(),
                SeatValue.builder().seatId(2L).seatNumber(2).seatStatus("AVAILABLE").seatPrice(100000).build(),
                SeatValue.builder().seatId(3L).seatNumber(3).seatStatus("UNAVAILABLE").seatPrice(200000).build()
        );

        List<SeatValue> availableSeats = allSeats.stream()
                .filter(seat -> "AVAILABLE".equals(seat.getSeatStatus()))
                .toList();

        SeatResponse response = SeatResponse.builder()
                .concertId(concertId)
                .scheduleId(scheduleId)
                .allSeats(allSeats)
                .availableSeats(availableSeats)
                .build();

        return ResponseEntity.ok(response);
    }
}
