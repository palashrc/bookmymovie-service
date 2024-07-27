package com.bookmymovie.orchestrator.controllers;

import com.bookmymovie.orchestrator.aspect.TrackExecutionTime;
import com.bookmymovie.orchestrator.model.BookingRequest;
import com.bookmymovie.orchestrator.model.BookingResponseAck;
import com.bookmymovie.orchestrator.model.OrderResponseAsync;
import com.bookmymovie.orchestrator.service.OrchestratorAsyncService;
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

    @Autowired
    private OrchestratorAsyncService orchestratorAsyncService;

    @PostMapping("/book-new")
    @ResponseBody
    @TrackExecutionTime
    public BookingResponseAck createBooking(@RequestBody BookingRequest bookingRequest) { return orchestratorService.createBooking(bookingRequest); }

    @PostMapping("/book-async")
    @ResponseBody
    @TrackExecutionTime
    public OrderResponseAsync createAsyncBooking(@RequestBody OrderResponseAsync orderResponseAsync) { return orchestratorAsyncService.createAsyncBooking(orderResponseAsync); }
}
