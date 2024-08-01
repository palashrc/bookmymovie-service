package com.bookmymovie.theater.controllers;

import com.bookmymovie.theater.model.ScreenRequest;
import com.bookmymovie.theater.model.ScreenResponse;
import com.bookmymovie.theater.service.ScreenService;
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

@WebMvcTest(ScreenController.class)
class ScreenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScreenService mockScreenService;

    @Test
    void testAddScreen() throws Exception {
        // Setup
        when(mockScreenService.saveScreen(ScreenRequest.builder().build()))
                .thenReturn(ScreenResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/screen/screen-new")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testUpdateScreenOperational() throws Exception {
        // Setup
        when(mockScreenService.updateScreenOperational(ScreenRequest.builder().build()))
                .thenReturn(ScreenResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/screen/screen-operation-configure")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
