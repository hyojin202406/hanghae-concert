package com.hhplu.hhplusconcert.app.interfaces.api.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplu.hhplusconcert.app.interfaces.api.reservation.req.ReservationRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

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
    void 예약_성공() throws Exception {
        // Given
        Long userId = 1L;
        Long concertId = 1L;
        Long scheduleId = 1L;
        Long[] seatIds = {1L, 2L};

        ReservationRequest request = new ReservationRequest(userId, concertId, scheduleId, seatIds);
        String requestBody = objectMapper.writeValueAsString(request);

        String queueToken = "d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a4";

        // When & Then
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .header("QUEUE-TOKEN", queueToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId", is(notNullValue())))
                .andExpect(jsonPath("$.concertId", is(1)))
                .andExpect(jsonPath("$.concertName", is("Concert A")))
                .andExpect(jsonPath("$.seats", hasSize(2)))
                .andExpect(jsonPath("$.totalPrice", is(200)))
                .andExpect(jsonPath("$.reservationStatus", is("TEMPORARY_RESERVED")));
    }

}