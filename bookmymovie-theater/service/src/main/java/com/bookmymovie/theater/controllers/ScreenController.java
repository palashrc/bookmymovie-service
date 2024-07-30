package com.bookmymovie.theater.controllers;

import com.bookmymovie.theater.aspect.TrackExecutionTime;
import com.bookmymovie.theater.model.ScreenRequest;
import com.bookmymovie.theater.model.ScreenResponse;
import com.bookmymovie.theater.model.TheaterRequest;
import com.bookmymovie.theater.model.TheaterResponse;
import com.bookmymovie.theater.service.ScreenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/screen")
@Slf4j
public class ScreenController {

    @Autowired
    private ScreenService screenService;

    @PostMapping("/screen-new")
    @ResponseBody
    @TrackExecutionTime
    public ScreenResponse addScreen(@RequestBody ScreenRequest screenRequest) { return screenService.saveScreen(screenRequest); }

    @PostMapping("/screen-operation-configure")
    @ResponseBody
    @TrackExecutionTime
    public ScreenResponse updateScreenOperational(@RequestBody ScreenRequest screenRequest) { return screenService.updateScreenOperational(screenRequest); }
}
