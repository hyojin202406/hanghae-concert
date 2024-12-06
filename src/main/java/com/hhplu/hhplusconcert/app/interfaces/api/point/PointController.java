package com.hhplu.hhplusconcert.app.interfaces.api.point;

import com.hhplu.hhplusconcert.app.application.service.point.PointService;
import com.hhplu.hhplusconcert.app.application.service.point.dto.GetPointDto;
import com.hhplu.hhplusconcert.app.application.service.point.dto.RechargeDto;
import com.hhplu.hhplusconcert.app.interfaces.api.point.req.PointRequest;
import com.hhplu.hhplusconcert.app.interfaces.api.point.res.PointResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/points")
public class PointController {

    private final PointService pointService;

    @Operation(summary = "잔액 충전", description = "사용자의 포인트 잔액을 충전합니다.")
    @ApiResponse(responseCode = "200", description = "충전 성공")
    @PostMapping("/users/{userId}/recharge")
    public ResponseEntity<PointResponse> recharge(
            @Parameter(description = "사용자 ID") @PathVariable("userId") Long userId,
            @Parameter(description = "충전 요청 정보") @RequestBody PointRequest request) {
        GetPointDto command = pointService.rechargePoint(new RechargeDto(userId, request.getPointAmount()));
        return ResponseEntity.ok(PointResponse.from(userId, command));
    }

    @Operation(summary = "잔액 조회", description = "사용자의 포인트 잔액을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "잔액 조회 성공")
    @PostMapping("/users/{userId}")
    public ResponseEntity<PointResponse> point(
            @Parameter(description = "사용자 ID") @PathVariable("userId") Long userId) {
        GetPointDto command = pointService.getPoint(userId);
        return ResponseEntity.ok(PointResponse.from(userId, command));
    }
}
