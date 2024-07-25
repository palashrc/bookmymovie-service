package com.bookmymovie.payment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentGatewayProcessorService {

    public Boolean connectPaymentGateway() throws InterruptedException {
        log.info("Connecting with Payment Gateway...");
        Thread.sleep(7000);
        log.info("Payment Processed!");
        return  Boolean.TRUE;
    }
}
