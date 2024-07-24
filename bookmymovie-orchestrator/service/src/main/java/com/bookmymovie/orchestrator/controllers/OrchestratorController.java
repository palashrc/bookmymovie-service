package com.bookmymovie.orchestrator.controllers;

import com.bookmymovie.orchestrator.model.BookingRequest;
import com.bookmymovie.orchestrator.model.BookingResponse;
import com.bookmymovie.orchestrator.service.OrchestratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orch")
@Slf4j
public class OrchestratorController {
    @Autowired
    private OrchestratorService orchestratorService;

    @PostMapping("/book-new")
    @ResponseBody
    public BookingResponse addViewer(@RequestBody BookingRequest bookingRequest) { return orchestratorService.createBooking(bookingRequest); }
}
