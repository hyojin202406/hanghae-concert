package com.hhplu.hhplusconcert.app.interfaces.api.waitingqueue;

import com.hhplu.hhplusconcert.app.application.facade.WaitingQueueFacade;
import com.hhplu.hhplusconcert.app.application.service.WaitingQueueService;
import com.hhplu.hhplusconcert.app.application.waitingqueue.CreateWaitingQueueCommand;
import com.hhplu.hhplusconcert.app.application.waitingqueue.GetWaitingQueueCommand;
import com.hhplu.hhplusconcert.app.domain.watingqueue.entity.WaitingQueue;
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

    private final WaitingQueueService waitingQueueService;
    private final WaitingQueueFacade waitingQueueFacade;

    /**
     * 유저 토큰 발급
     * @param userId
     * @return
     */
    @Operation(summary = "유저 토큰 발급", description = "사용자에게 대기열 토큰을 발급합니다.")
    @ApiResponse(responseCode = "200", description = "토큰 발급 성공")
    @PostMapping("/tokens/users/{userId}")
    public ResponseEntity<TokenResponse> token(
            @Parameter(description = "사용자 ID") @PathVariable Long userId) {
        CreateWaitingQueueCommand command = waitingQueueFacade.token(userId);
        TokenResponse response = TokenResponse.builder()
                .userId(userId)
                .queueToken(command.getWaitingQueue().getQueueToken())
                .issuedAt(command.getWaitingQueue().getIssuedAt())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * 대기열 정보 조회
     * @return
     */
    @Operation(summary = "대기열 정보 조회", description = "사용자의 대기열 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "대기열 정보 조회 성공")
    @GetMapping("/tokens/users")
    public ResponseEntity<QueueResponse> queue(@RequestHeader("QUEUE-TOKEN") String queueToken) {
        GetWaitingQueueCommand command = waitingQueueFacade.queue(queueToken);
        QueueResponse response = QueueResponse.builder()
                .userId(command.getWaitingQueue().getUserId())
                .queueToken(queueToken)
                .queuePosition(command.getWaitingQueue().getId())
                .lastActivedQueuePosition(command.getLastActiveId())
                .queueStatus(command.getWaitingQueue().getQueueStatus())
                .issuedAt(command.getWaitingQueue().getIssuedAt())
                .build();
        return ResponseEntity.ok(response);
    }
}
