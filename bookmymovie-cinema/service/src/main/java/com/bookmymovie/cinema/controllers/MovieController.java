package com.bookmymovie.cinema.controllers;

import com.bookmymovie.cinema.model.MovieRequest;
import com.bookmymovie.cinema.model.MovieResponse;
import com.bookmymovie.cinema.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
@Slf4j
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/movie-new")
    @ResponseBody
    public MovieResponse createMovie(@RequestBody MovieRequest movieRequest) { return movieService.saveMovie(movieRequest); }
}
