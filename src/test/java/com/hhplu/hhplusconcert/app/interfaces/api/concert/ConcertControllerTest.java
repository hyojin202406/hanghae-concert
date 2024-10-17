package com.hhplu.hhplusconcert.app.interfaces.api.concert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    void 콘서트_일정_조회_성공() throws Exception {
        // Given
        Long concertId = 1L;
        String queueToken = "d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a3";

        // When & Then
        mockMvc.perform(get("/api/concerts/{concertId}/schedules", concertId)
                        .header("QUEUE-TOKEN", queueToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concertId").value(concertId))
                .andExpect(jsonPath("$.events").isArray())
                .andExpect(jsonPath("$.events[0].scheduleId").exists())
                .andExpect(jsonPath("$.events[0].scheduleStartedAt").exists());
    }

    @Test
    void 콘서트_좌석_조회_성공() throws Exception {
        // Given
        Long concertId = 1L;
        Long scheduleId = 1L;
        String queueToken = "d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a3";

        // When & Then
        mockMvc.perform(get("/api/concerts/{concertId}/schedules/{scheduleId}/seats", concertId, scheduleId)
                        .header("QUEUE-TOKEN", queueToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concertId").value(concertId))
                .andExpect(jsonPath("$.scheduleId").value(scheduleId))
                .andExpect(jsonPath("$.allSeats").isArray())
                .andExpect(jsonPath("$.availableSeats").isArray());
    }
}