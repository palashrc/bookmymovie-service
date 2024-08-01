package com.bookmymovie.viewer.controllers;

import com.bookmymovie.viewer.model.ViewerRequest;
import com.bookmymovie.viewer.model.ViewerResponse;
import com.bookmymovie.viewer.service.ViewerService;
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

@WebMvcTest(ViewerController.class)
class ViewerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ViewerService mockViewerService;

    @Test
    void testAddViewer() throws Exception {
        // Setup
        when(mockViewerService.createViewer(ViewerRequest.builder().build()))
                .thenReturn(ViewerResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/viewer/viewer-new")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetViewerByMobile() throws Exception {
        // Setup
        when(mockViewerService.getViewerByMobile(ViewerRequest.builder().build()))
                .thenReturn(ViewerResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/viewer/viewer-by-mobile")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
