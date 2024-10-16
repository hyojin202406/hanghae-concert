package com.hhplu.hhplusconcert.app.interfaces.api.point;


import com.hhplu.hhplusconcert.app.interfaces.api.point.req.PointRequest;
import com.hhplu.hhplusconcert.app.interfaces.api.point.res.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/points")
public class PointController {

    /**
     * 잔액 충전
     * @param userId
     * @param request
     * @return
     */
    @PostMapping("/point/users/{userId}/recharge")
    public ResponseEntity<PointResponse> recharge(@PathVariable("userId") Long userId,
                                                  @RequestBody PointRequest request) {
        PointResponse response = PointResponse.builder()
                .userId(1L)
                .currentPointAmount(40000L)
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * 잔액 조회
     * @param userId
     * @return
     */
    @PostMapping("/point/users/{userId}")
    public ResponseEntity<PointResponse> point(@PathVariable("userId") Long userId) {
        PointResponse response = PointResponse.builder()
                .userId(1L)
                .currentPointAmount(40000L)
                .build();
        return ResponseEntity.ok(response);
    }
}
