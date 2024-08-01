package com.bookmymovie.orchestrator.controllers;

import com.bookmymovie.orchestrator.model.BookingRequest;
import com.bookmymovie.orchestrator.model.BookingResponseAck;
import com.bookmymovie.orchestrator.model.OrderResponseAsync;
import com.bookmymovie.orchestrator.service.OrchestratorAsyncService;
import com.bookmymovie.orchestrator.service.OrchestratorService;
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

@WebMvcTest(OrchestratorController.class)
class OrchestratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrchestratorService mockOrchestratorService;
    @MockBean
    private OrchestratorAsyncService mockOrchestratorAsyncService;

    @Test
    void testCreateBooking() throws Exception {
        // Setup
        when(mockOrchestratorService.createBooking(BookingRequest.builder().build()))
                .thenReturn(BookingResponseAck.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/orch/book-new")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testCreateAsyncBooking() throws Exception {
        // Setup
        when(mockOrchestratorAsyncService.createAsyncBooking(OrderResponseAsync.builder().build()))
                .thenReturn(OrderResponseAsync.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/orch/book-async")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
