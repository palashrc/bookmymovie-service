package com.bookmymovie.theater.controllers;

import com.bookmymovie.theater.model.CityRequest;
import com.bookmymovie.theater.model.CityResponse;
import com.bookmymovie.theater.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
@Slf4j
public class CityController {
    @Autowired
    private CityService cityService;

    @PostMapping("/city-add")
    @ResponseBody
    public CityResponse addCity(@RequestBody CityRequest cityRequest) {
        return cityService.saveCity(cityRequest);
    }

    @GetMapping("/city-all")
    @ResponseBody
    public CityResponse getCity() { return cityService.getCity(); }

    @PostMapping("/city-by-id")
    @ResponseBody
    public CityResponse getCityById(@RequestBody CityRequest cityRequest) { return cityService.getCityById(cityRequest); }

    @PostMapping("/city-by-name")
    @ResponseBody
    public CityResponse getCityByName(@RequestBody CityRequest cityRequest) { return cityService.getCityByName(cityRequest); }

    @PostMapping("/city-operation-configure")
    @ResponseBody
    public CityResponse updateCityOperational(@RequestBody CityRequest cityRequest) { return cityService.updateCityOperational(cityRequest); }
}
