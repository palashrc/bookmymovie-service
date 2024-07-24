package com.bookmymovie.order.controllers;

import com.bookmymovie.order.model.OrderRequest;
import com.bookmymovie.order.model.OrderResponse;
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

    @PostMapping("/order-new")
    @ResponseBody
    public OrderResponse addViewer(@RequestBody OrderRequest orderRequest) { return orderService.createOrder(orderRequest); }
}
