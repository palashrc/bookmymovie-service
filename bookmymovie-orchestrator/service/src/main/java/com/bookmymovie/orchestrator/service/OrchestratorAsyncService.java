package com.bookmymovie.orchestrator.service;

import com.bookmymovie.orchestrator.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrchestratorAsyncService {

    @Autowired
    private OrchRequestTrackerService orchRequestTrackerService;

    public OrderResponseAsync createAsyncBooking(OrderResponseAsync async) {
        log.info("Order Microservice Asynchronous Response: " + async);
        orchRequestTrackerService.updateTrackerForFinalRes(async);
        return async;
    }
}
