package com.bookmymovie.orchestrator.service;

import com.bookmymovie.orchestrator.model.BookingRequest;
import com.bookmymovie.orchestrator.model.BookingResponse;
import com.bookmymovie.orchestrator.model.order.Order;
import com.bookmymovie.orchestrator.model.order.OrderRequest;
import com.bookmymovie.orchestrator.model.order.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OrchestratorService {

    @Autowired
    private RestTemplate restTemplate;

    public BookingResponse createBooking(BookingRequest bookingRequest) {
        BookingResponse bookingResponse = new BookingResponse();

        OrderRequest orderRequest = new OrderRequest();
        Order order = new Order();
        orderRequest.setOrder(order);

        HttpEntity<OrderRequest> orderRequestHttpEntity = new HttpEntity<OrderRequest>(orderRequest);

        ResponseEntity<OrderResponse> orderResponseEntity = restTemplate.exchange("http://localhost:8085/order/order-new", HttpMethod.POST, orderRequestHttpEntity, OrderResponse.class);
        if (orderResponseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Order Microservice Response : " + orderResponseEntity.getBody());
        } else {
            log.error("Errors!");
        }

        return bookingResponse;

    }
}
