package com.bookmymovie.theater.controllers;

import com.bookmymovie.theater.model.SeatRequest;
import com.bookmymovie.theater.model.SeatResponse;
import com.bookmymovie.theater.service.SeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seat")
@Slf4j
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping("/seats-load")
    @ResponseBody
    public SeatResponse createSeats(@RequestBody SeatRequest seatRequest) { return seatService.createSeats(seatRequest); }
}
