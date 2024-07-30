package com.bookmymovie.cinema.controllers;

import com.bookmymovie.cinema.aspect.TrackExecutionTime;
import com.bookmymovie.cinema.model.MovieRequest;
import com.bookmymovie.cinema.model.MovieResponse;
import com.bookmymovie.cinema.model.MovieShowRequest;
import com.bookmymovie.cinema.model.MovieShowResponse;
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
    @TrackExecutionTime
    public MovieResponse createMovie(@RequestBody MovieRequest movieRequest) { return movieService.saveMovie(movieRequest); }

    @PostMapping("/movie-by-id")
    @ResponseBody
    @TrackExecutionTime
    public MovieResponse getMovieById(@RequestBody MovieRequest movieRequest) { return movieService.getMovieById(movieRequest); }

    @PostMapping("/movie-operation-configure")
    @ResponseBody
    @TrackExecutionTime
    public MovieResponse updateMovieOperational(@RequestBody MovieRequest movieRequest) { return movieService.updateMovieOperational(movieRequest); }

    @PostMapping("/usr/movie-by-id")
    @ResponseBody
    @TrackExecutionTime
    public MovieResponse getMovieByIdForUser(@RequestBody MovieRequest movieRequest) { return movieService.getMovieByIdForUser(movieRequest); }
}
