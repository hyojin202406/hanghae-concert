package com.hhplu.hhplusconcert.interfaces.api.queue;

import com.hhplu.hhplusconcert.interfaces.api.queue.req.QueueRequest;
import com.hhplu.hhplusconcert.interfaces.api.queue.req.TokenRequest;
import com.hhplu.hhplusconcert.interfaces.api.queue.res.QueueResponse;
import com.hhplu.hhplusconcert.interfaces.api.queue.res.TokenResponse;
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
     * @param request
     * @return
     */
    @PostMapping("/tokens/users/{userId}")
    public ResponseEntity<TokenResponse> token(@PathVariable Long userId, @RequestBody TokenRequest request) {
        TokenResponse response = TokenResponse.builder()
                .userId(12345L)
                .queueToken("d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a5")
                .issuedAt(LocalDateTime.parse("2024-10-08T10:25:00Z"))
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * 대기열 정보 조회
     * @param request
     * @return
     */
    @GetMapping("/tokens/users")
    public ResponseEntity<QueueResponse> queue(@RequestBody QueueRequest request) {
        QueueResponse response = QueueResponse.builder()
                .userId(12345L)
                .queueToken("d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a5")
                .queuePosition(12345L)
                .queueStatus("WAITING")
                .issuedAt(LocalDateTime.parse("2024-10-08T10:25:00Z"))
                .build();
        return ResponseEntity.ok(response);
    }
}
