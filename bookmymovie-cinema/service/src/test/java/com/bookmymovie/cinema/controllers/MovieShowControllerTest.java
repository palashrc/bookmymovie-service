package com.bookmymovie.cinema.controllers;

import com.bookmymovie.cinema.model.MovieShowRequest;
import com.bookmymovie.cinema.model.MovieShowResponse;
import com.bookmymovie.cinema.service.MovieShowService;
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

@WebMvcTest(MovieShowController.class)
class MovieShowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieShowService mockMovieShowService;

    @Test
    void testCreateMovie() throws Exception {
        // Setup
        when(mockMovieShowService.createMovieShow(MovieShowRequest.builder().build()))
                .thenReturn(MovieShowResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/movieshow/movieshow-new")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testUpdateMovieShowOperational() throws Exception {
        // Setup
        when(mockMovieShowService.updateMovieShowOperational(MovieShowRequest.builder().build()))
                .thenReturn(MovieShowResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/movieshow/movieshow-operation-configure")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetMovieShowByCityAndMovie() throws Exception {
        // Setup
        when(mockMovieShowService.getMovieShowByCityAndMovie(MovieShowRequest.builder().build()))
                .thenReturn(MovieShowResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/movieshow/movieshow-by-citymovie")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
