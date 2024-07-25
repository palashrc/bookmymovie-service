package com.bookmymovie.order.controllers;

import com.bookmymovie.order.model.OrderRequest;
import com.bookmymovie.order.model.OrderResponseAck;
import com.bookmymovie.order.model.PaymentResponseAsync;
import com.bookmymovie.order.service.OrderAsyncService;
import com.bookmymovie.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    OrderAsyncService orderAsyncService;

    @PostMapping("/order-new")
    @ResponseBody
    public OrderResponseAck processOrder(@RequestBody OrderRequest orderRequest) { return orderService.createOrder(orderRequest); }

    @PostMapping("/order-async")
    @ResponseBody
    public PaymentResponseAsync processAsyncOrder(@RequestBody PaymentResponseAsync paymentResponseAsync) { return orderAsyncService.processAsyncOrder(paymentResponseAsync); }
}
