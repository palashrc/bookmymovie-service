package com.bookmymovie.theater.controllers;

import com.bookmymovie.theater.model.ScreenRequest;
import com.bookmymovie.theater.model.ScreenResponse;
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
    public ScreenResponse addScreen(@RequestBody ScreenRequest screenRequest) { return screenService.saveScreen(screenRequest); }

    @PostMapping("/screen-by-id")
    @ResponseBody
    public ScreenResponse getScreenById(@RequestBody ScreenRequest screenRequest) { return screenService.getScreenById(screenRequest); }
}
