package com.bookmymovie.theater.controllers;

import com.bookmymovie.theater.aspect.TrackExecutionTime;
import com.bookmymovie.theater.model.CityRequest;
import com.bookmymovie.theater.model.CityResponse;
import com.bookmymovie.theater.model.TheaterRequest;
import com.bookmymovie.theater.model.TheaterResponse;
import com.bookmymovie.theater.service.TheaterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theater")
@Slf4j
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @PostMapping("/theater-new")
    @ResponseBody
    @TrackExecutionTime
    public TheaterResponse addTheater(@RequestBody TheaterRequest theaterRequest) { return theaterService.saveTheater(theaterRequest); }

    @PostMapping("/theater-operation-configure")
    @ResponseBody
    @TrackExecutionTime
    public TheaterResponse updateTheaterOperational(@RequestBody TheaterRequest theaterRequest) { return theaterService.updateTheaterOperational(theaterRequest); }
}
