package com.hhplu.hhplusconcert.app.interfaces.api.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplu.hhplusconcert.app.application.facade.PaymentFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentFacade paymentFacade;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String ACTIVE_QUEUE_KEY = "activeUserQueue";

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        redisTemplate.opsForList().rightPush(ACTIVE_QUEUE_KEY, "2d08cf07-349e-3537-b91c-f69e16977f60");
    }

    @Test
    public void 결제_성공() throws Exception {
        Long userId = 1L;
        Long paymentId = 1L;
        String queueToken = "2d08cf07-349e-3537-b91c-f69e16977f60";

        mockMvc.perform(post("/api/payments/{paymentId}/users/{userId}", paymentId, userId)
                        .header("QUEUE-TOKEN", queueToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(paymentId))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.paymentStatus").value("COMPLETED"));
    }

    @Test
    public void 결제_내역_조회_성공() throws Exception {
        Long userId = 2L;
        String queueToken = "2d08cf07-349e-3537-b91c-f69e16977f60";

        mockMvc.perform(post("/api/payments/history/users/{userId}", userId)
                        .header("QUEUE-TOKEN", queueToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.payments").isArray())
                .andExpect(jsonPath("$.payments").isNotEmpty());
    }
}