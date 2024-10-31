package com.hhplu.hhplusconcert.app.application.Concurrency;

import com.hhplu.hhplusconcert.app.application.facade.PointFacade;
import com.hhplu.hhplusconcert.app.application.service.point.command.RechargeCommand;
import com.hhplu.hhplusconcert.app.domain.point.entity.Point;
import com.hhplu.hhplusconcert.app.domain.point.repository.PointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PointServiceConcurrencyTest {

    @Autowired
    private PointFacade pointFacade;

    @Autowired
    private PointRepository pointRepository;

    @Test
    void 포인트_충전_동시성_제어() throws InterruptedException {
        int THREAD_COUNT = 1000;

        RechargeCommand command = new RechargeCommand(1L, 100L);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    pointFacade.rechargePoint(command);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Point point = pointRepository.point(1L);

        // DB에는 1000 포인트가 저장되어있습니다.
        assertThat(point.getPointAmount()).isEqualTo(new BigDecimal("101000.00"));

    }

}
