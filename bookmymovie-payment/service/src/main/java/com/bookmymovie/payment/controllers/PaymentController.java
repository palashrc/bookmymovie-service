package com.bookmymovie.payment.controllers;

import com.bookmymovie.payment.model.PaymentRequest;
import com.bookmymovie.payment.model.PaymentResponseAck;
import com.bookmymovie.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pay")
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment-new")
    @ResponseBody
    public PaymentResponseAck addViewer(@RequestBody PaymentRequest paymentRequest) { return paymentService.createPayment(paymentRequest); }
}
