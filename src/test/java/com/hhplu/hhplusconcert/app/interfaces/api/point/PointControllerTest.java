package com.hhplu.hhplusconcert.app.interfaces.api.point;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplu.hhplusconcert.app.interfaces.api.point.req.PointRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    void 잔액_충전_성공() throws Exception {
        // Given
        Long userId = 1L;
        PointRequest pointRequest = new PointRequest(500L);
        String requestBody = objectMapper.writeValueAsString(pointRequest);

        // When & Then
        mockMvc.perform(post("/api/points/users/{userId}/recharge", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPointAmount").value("1500.0"));
    }

    @Test
    void 잔액_조회_성공() throws Exception {
        // Given
        Long userId = 1L;

        // When & Then
        mockMvc.perform(post("/api/points/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPointAmount").value("1000.0"));
    }
}