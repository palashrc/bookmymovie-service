package com.bookmymovie.theater.controllers;

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

    @PostMapping("/theater-add")
    @ResponseBody
    public TheaterResponse addTheater(@RequestBody TheaterRequest theaterRequest) { return theaterService.saveCity(theaterRequest); }

    @GetMapping("/theater-all")
    @ResponseBody
    public TheaterResponse getTheater() { return theaterService.getTheater(); }
}
