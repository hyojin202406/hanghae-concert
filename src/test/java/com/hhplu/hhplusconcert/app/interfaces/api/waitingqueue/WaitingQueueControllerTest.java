package com.hhplu.hhplusconcert.app.interfaces.api.waitingqueue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class WaitingQueueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String ACTIVE_QUEUE_KEY = "activeUserQueue";

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        redisTemplate.opsForList().rightPush(ACTIVE_QUEUE_KEY, "2d08cf07-349e-3537-b91c-f69e16977f60");
    }

    @AfterEach
    public void cleraToken() {
        // activeUserQueue에서 해당 토큰을 제거
        String queueToken = "2d08cf07-349e-3537-b91c-f69e16977f60";
        redisTemplate.opsForList().remove(ACTIVE_QUEUE_KEY, 1, queueToken);
    }

    @Test
    void 유저_토큰_발급_성공() throws Exception {
        final String url = "/api/queue/tokens/users/1";
        ResultActions resultActions = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 대기열_정보_조회_성공() throws Exception {
        String queueToken = "2d08cf07-349e-3537-b91c-f69e16977f60";
        String url = "/api/queue/tokens/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("QUEUE-TOKEN", queueToken);

        ResultActions resultActions = mockMvc.perform(get(url)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
    }

}