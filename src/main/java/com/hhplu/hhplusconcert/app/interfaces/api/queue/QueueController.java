package com.hhplu.hhplusconcert.app.interfaces.api.queue;

import com.hhplu.hhplusconcert.app.interfaces.api.queue.res.QueueResponse;
import com.hhplu.hhplusconcert.app.interfaces.api.queue.res.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/queue")
public class QueueController {

    /**
     * 유저 토큰 발급
     * @param userId
     * @return
     */
    @PostMapping("/tokens/users/{userId}")
    public ResponseEntity<TokenResponse> token(@PathVariable Long userId) {
        TokenResponse response = TokenResponse.builder()
                .userId(12345L)
                .queueToken("d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a5")
                .issuedAt(LocalDateTime.of(2024,10,8,10,0,0))
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * 대기열 정보 조회
     * @return
     */
    @GetMapping("/tokens/users")
    public ResponseEntity<QueueResponse> queue() {
        QueueResponse response = QueueResponse.builder()
                .userId(12345L)
                .queueToken("d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a5")
                .queuePosition(5000L)
                .lastActivedQueuePosition(1000L)
                .queueStatus("WAITING")
                .issuedAt(LocalDateTime.of(2024,10,8,10,0,0))
                .build();
        return ResponseEntity.ok(response);
    }
}
