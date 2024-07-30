package com.bookmymovie.theater.controllers;

import com.bookmymovie.theater.aspect.TrackExecutionTime;
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

    @PostMapping("/city-new")
    @ResponseBody
    @TrackExecutionTime
    public CityResponse addCity(@RequestBody CityRequest cityRequest) {
        return cityService.saveCity(cityRequest);
    }

    @GetMapping("/city-all")
    @ResponseBody
    @TrackExecutionTime
    public CityResponse getCity() { return cityService.getCity(); }

    @PostMapping("/city-by-id")
    @ResponseBody
    @TrackExecutionTime
    public CityResponse getCityById(@RequestBody CityRequest cityRequest) { return cityService.getCityById(cityRequest); }

    @PostMapping("/city-by-name")
    @ResponseBody
    @TrackExecutionTime
    public CityResponse getCityByName(@RequestBody CityRequest cityRequest) { return cityService.getCityByName(cityRequest); }

    @PostMapping("/city-operation-configure")
    @ResponseBody
    @TrackExecutionTime
    public CityResponse updateCityOperational(@RequestBody CityRequest cityRequest) { return cityService.updateCityOperational(cityRequest); }

    @GetMapping("/usr/city-all")
    @ResponseBody
    @TrackExecutionTime
    public CityResponse getCityForUser() { return cityService.getCityForUser(); }

    @PostMapping("/usr/city-by-id")
    @ResponseBody
    @TrackExecutionTime
    public CityResponse getCityByIdForUser(@RequestBody CityRequest cityRequest) { return cityService.getCityByIdForUser(cityRequest); }

    @PostMapping("/usr/city-by-name")
    @ResponseBody
    @TrackExecutionTime
    public CityResponse getCityByNameForUser(@RequestBody CityRequest cityRequest) { return cityService.getCityByNameForUser(cityRequest); }
}
