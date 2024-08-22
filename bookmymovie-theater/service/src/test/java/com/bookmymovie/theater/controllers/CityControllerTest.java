package com.bookmymovie.theater.controllers;

import com.bookmymovie.theater.model.CityRequest;
import com.bookmymovie.theater.model.CityResponse;
import com.bookmymovie.theater.service.CityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CityControllerTest {

    @Mock
    CityService cityService;

    @InjectMocks
    CityController cityController;

    @Test
    void testAddCity() throws Exception {
        CityResponse cityResponse = cityController.addCity(CityRequest.builder().build());
        Assertions.assertNull(cityResponse);
    }
}
