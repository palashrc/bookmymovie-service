package com.bookmymovie.theater.controllers;

import com.bookmymovie.theater.model.SeatRequest;
import com.bookmymovie.theater.model.SeatResponse;
import com.bookmymovie.theater.service.SeatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SeatController.class)
class SeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeatService mockSeatService;

    @Test
    void testCreateSeats() throws Exception {
        // Setup
        when(mockSeatService.createSeats(SeatRequest.builder().build())).thenReturn(SeatResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/seat/seats-new")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testUpdateSeatOperational() throws Exception {
        // Setup
        when(mockSeatService.updateSeatOperational(SeatRequest.builder().build()))
                .thenReturn(SeatResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/seat/seat-operation-configure")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
