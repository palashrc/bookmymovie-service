package com.bookmymovie.order.service;

import com.bookmymovie.order.model.OrderRequest;
import com.bookmymovie.order.model.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    public OrderResponse createOrder(OrderRequest orderRequest) {
        OrderResponse orderResponse = new OrderResponse();
        log.info("Order received...");
        return orderResponse;
    }

}
