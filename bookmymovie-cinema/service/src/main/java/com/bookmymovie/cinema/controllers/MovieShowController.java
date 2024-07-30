package com.bookmymovie.cinema.controllers;

import com.bookmymovie.cinema.aspect.TrackExecutionTime;
import com.bookmymovie.cinema.model.MovieShowRequest;
import com.bookmymovie.cinema.model.MovieShowResponse;
import com.bookmymovie.cinema.service.MovieShowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movieshow")
@Slf4j
public class MovieShowController {

    @Autowired
    private MovieShowService movieShowService;

    @PostMapping("/movieshow-new")
    @ResponseBody
    @TrackExecutionTime
    public MovieShowResponse createMovie(@RequestBody MovieShowRequest movieShowRequest) { return movieShowService.createMovieShow(movieShowRequest); }

    @PostMapping("/movieshow-operation-configure")
    @ResponseBody
    @TrackExecutionTime
    public MovieShowResponse updateMovieShowOperational(@RequestBody MovieShowRequest movieShowRequest) { return movieShowService.updateMovieShowOperational(movieShowRequest); }

    @PostMapping("/movieshow-by-citymovie")
    @ResponseBody
    @TrackExecutionTime
    public MovieShowResponse getMovieShowByCityAndMovie(@RequestBody MovieShowRequest movieShowRequest) { return movieShowService.getMovieShowByCityAndMovie(movieShowRequest); }
}
