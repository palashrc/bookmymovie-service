package com.bookmymovie.theater.controllers;

import com.bookmymovie.theater.model.TheaterRequest;
import com.bookmymovie.theater.model.TheaterResponse;
import com.bookmymovie.theater.service.TheaterService;
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

@WebMvcTest(TheaterController.class)
class TheaterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TheaterService mockTheaterService;

    @Test
    void testAddTheater() throws Exception {
        // Setup
        when(mockTheaterService.saveTheater(TheaterRequest.builder().build()))
                .thenReturn(TheaterResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/theater/theater-new")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testUpdateTheaterOperational() throws Exception {
        // Setup
        when(mockTheaterService.updateTheaterOperational(TheaterRequest.builder().build()))
                .thenReturn(TheaterResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/theater/theater-operation-configure")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
