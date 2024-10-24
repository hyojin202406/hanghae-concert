package com.hhplu.hhplusconcert.app.interfaces.api.point;

import com.hhplu.hhplusconcert.app.application.service.point.command.GetPointCommand;
import com.hhplu.hhplusconcert.app.application.service.point.command.RechargeCommand;
import com.hhplu.hhplusconcert.app.application.service.point.service.PointService;
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

    /**
     * 잔액 충전
     * @param userId
     * @param request
     * @return
     */
    @Operation(summary = "잔액 충전", description = "사용자의 포인트 잔액을 충전합니다.")
    @ApiResponse(responseCode = "200", description = "충전 성공")
    @PostMapping("/users/{userId}/recharge")
    public ResponseEntity<PointResponse> recharge(
            @Parameter(description = "사용자 ID") @PathVariable("userId") Long userId,
            @Parameter(description = "충전 요청 정보") @RequestBody PointRequest request) {
        GetPointCommand command = pointService.rechargePoint(new RechargeCommand(userId, request.getPointAmount()));
        return ResponseEntity.ok(PointResponse.from(userId, command));
    }

    /**
     * 잔액 조회
     * @param userId
     * @return
     */
    @Operation(summary = "잔액 조회", description = "사용자의 포인트 잔액을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "잔액 조회 성공")
    @PostMapping("/users/{userId}")
    public ResponseEntity<PointResponse> point(
            @Parameter(description = "사용자 ID") @PathVariable("userId") Long userId) {
        GetPointCommand command = pointService.getPoint(userId);
        return ResponseEntity.ok(PointResponse.from(userId, command));
    }
}
