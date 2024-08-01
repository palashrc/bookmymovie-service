package com.bookmymovie.theater.controllers;

import com.bookmymovie.theater.model.CityRequest;
import com.bookmymovie.theater.model.CityResponse;
import com.bookmymovie.theater.service.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService mockCityService;

    @Test
    void testAddCity() throws Exception {
        // Setup
        when(mockCityService.saveCity(CityRequest.builder().build())).thenReturn(CityResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/city/city-new")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetCity() throws Exception {
        // Setup
        when(mockCityService.getCity()).thenReturn(CityResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(get("/city/city-all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetCityById() throws Exception {
        // Setup
        when(mockCityService.getCityById(CityRequest.builder().build())).thenReturn(CityResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/city/city-by-id")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetCityByName() throws Exception {
        // Setup
        when(mockCityService.getCityByName(CityRequest.builder().build())).thenReturn(CityResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/city/city-by-name")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testUpdateCityOperational() throws Exception {
        // Setup
        when(mockCityService.updateCityOperational(CityRequest.builder().build()))
                .thenReturn(CityResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/city/city-operation-configure")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetCityForUser() throws Exception {
        // Setup
        when(mockCityService.getCityForUser()).thenReturn(CityResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(get("/city/usr/city-all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetCityByIdForUser() throws Exception {
        // Setup
        when(mockCityService.getCityByIdForUser(CityRequest.builder().build()))
                .thenReturn(CityResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/city/usr/city-by-id")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetCityByNameForUser() throws Exception {
        // Setup
        when(mockCityService.getCityByNameForUser(CityRequest.builder().build()))
                .thenReturn(CityResponse.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/city/usr/city-by-name")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
