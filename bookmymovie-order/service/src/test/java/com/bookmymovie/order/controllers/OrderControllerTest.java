package com.bookmymovie.order.controllers;

import com.bookmymovie.order.model.OrderRequest;
import com.bookmymovie.order.model.OrderResponseAck;
import com.bookmymovie.order.model.PaymentResponseAsync;
import com.bookmymovie.order.service.OrderAsyncService;
import com.bookmymovie.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService mockOrderService;
    @MockBean
    private OrderAsyncService mockOrderAsyncService;

    @Test
    void testProcessOrder() throws Exception {
        // Setup
        when(mockOrderService.createOrder(OrderRequest.builder().build()))
                .thenReturn(OrderResponseAck.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/order/order-new")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testProcessAsyncOrder() throws Exception {
        // Setup
        when(mockOrderAsyncService.processAsyncOrder(PaymentResponseAsync.builder().build()))
                .thenReturn(PaymentResponseAsync.builder().build());

        // Run the test and verify the results
        mockMvc.perform(post("/order/order-async")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
