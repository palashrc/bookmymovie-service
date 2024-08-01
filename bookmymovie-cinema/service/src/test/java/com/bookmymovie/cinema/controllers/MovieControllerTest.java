package com.bookmymovie.cinema.controllers;

import com.bookmymovie.cinema.model.MovieRequest;
import com.bookmymovie.cinema.model.MovieResponse;
import com.bookmymovie.cinema.service.MovieService;
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

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService mockMovieService;

    @Test
    void testCreateMovie() throws Exception {
        // Setup
        when(mockMovieService.saveMovie(MovieRequest.builder().build())).thenReturn(MovieResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/movie/movie-new")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetMovieById() throws Exception {
        // Setup
        when(mockMovieService.getMovieById(MovieRequest.builder().build())).thenReturn(MovieResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/movie/movie-by-id")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testUpdateMovieOperational() throws Exception {
        // Setup
        when(mockMovieService.updateMovieOperational(MovieRequest.builder().build()))
                .thenReturn(MovieResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/movie/movie-operation-configure")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetMovieByIdForUser() throws Exception {
        // Setup
        when(mockMovieService.getMovieByIdForUser(MovieRequest.builder().build()))
                .thenReturn(MovieResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/movie/usr/movie-by-id")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
