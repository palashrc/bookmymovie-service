package com.bookmymovie.payment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentGatewayProcessorServiceTest {

    private PaymentGatewayProcessorService paymentGatewayProcessorServiceUnderTest;

    @BeforeEach
    void setUp() {
        paymentGatewayProcessorServiceUnderTest = new PaymentGatewayProcessorService();
    }

    @Test
    void testConnectPaymentGateway() throws Exception {
        assertThat(paymentGatewayProcessorServiceUnderTest.connectPaymentGateway()).isFalse();
    }
}
