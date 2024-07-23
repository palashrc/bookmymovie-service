package com.bookmymovie.cinema.controllers;

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

    @PostMapping("/movieshow-add")
    @ResponseBody
    public MovieShowResponse createMovie(@RequestBody MovieShowRequest movieShowRequest) { return movieShowService.createMovieShow(movieShowRequest); }
}
