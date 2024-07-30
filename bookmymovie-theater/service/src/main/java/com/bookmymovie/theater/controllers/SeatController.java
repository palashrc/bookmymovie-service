package com.bookmymovie.theater.controllers;

import com.bookmymovie.theater.aspect.TrackExecutionTime;
import com.bookmymovie.theater.model.ScreenRequest;
import com.bookmymovie.theater.model.ScreenResponse;
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

    @PostMapping("/seats-new")
    @ResponseBody
    @TrackExecutionTime
    public SeatResponse createSeats(@RequestBody SeatRequest seatRequest) { return seatService.createSeats(seatRequest); }

    @PostMapping("/seat-operation-configure")
    @ResponseBody
    @TrackExecutionTime
    public SeatResponse updateSeatOperational(@RequestBody SeatRequest seatRequest) { return seatService.updateSeatOperational(seatRequest); }
}
