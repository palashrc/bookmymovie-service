package com.bookmymovie.orchestrator.service;

import com.bookmymovie.orchestrator.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrchestratorAsyncService {

    public OrderResponseAsync createAsyncBooking(OrderResponseAsync orderResponseAsync) {
        log.info("Order Microservice Asynchronous Response:" + orderResponseAsync);
        return orderResponseAsync;
    }
}
