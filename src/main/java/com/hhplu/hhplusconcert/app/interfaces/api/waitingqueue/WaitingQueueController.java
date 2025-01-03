package com.hhplu.hhplusconcert.app.interfaces.api.waitingqueue;

import com.hhplu.hhplusconcert.app.application.facade.WaitingQueueFacade;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.dto.CreateWaitingQueueDto;
import com.hhplu.hhplusconcert.app.application.service.waitingqueue.dto.GetWaitingQueueDto;
import com.hhplu.hhplusconcert.app.interfaces.api.waitingqueue.res.QueueResponse;
import com.hhplu.hhplusconcert.app.interfaces.api.waitingqueue.res.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/queue")
public class WaitingQueueController {

    private final WaitingQueueFacade waitingQueueFacade;

    @Operation(summary = "유저 토큰 발급", description = "사용자에게 대기열 토큰을 발급합니다.")
    @ApiResponse(responseCode = "200", description = "토큰 발급 성공")
    @PostMapping("/tokens/users/{userId}")
    public ResponseEntity<TokenResponse> token(
            @Parameter(description = "사용자 ID") @PathVariable(name = "userId") Long userId) {
        CreateWaitingQueueDto command = waitingQueueFacade.token(userId);
        return ResponseEntity.ok(TokenResponse.from(command));
    }

    @Operation(summary = "대기열 정보 조회", description = "사용자의 대기열 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "대기열 정보 조회 성공")
    @GetMapping("/tokens/users")
    public ResponseEntity<QueueResponse> queue(@RequestHeader("QUEUE-TOKEN") String queueToken) {
        GetWaitingQueueDto command = waitingQueueFacade.queue(queueToken);
        return ResponseEntity.ok(QueueResponse.from(command));
    }
}
