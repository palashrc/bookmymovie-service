package com.bookmymovie.payment.service;

import com.bookmymovie.core.error.Error;
import com.bookmymovie.payment.converter.PaymentConverter;
import com.bookmymovie.payment.helper.StatusMapper;
import com.bookmymovie.payment.metrics.MetricsContainerService;
import com.bookmymovie.payment.model.Payment;
import com.bookmymovie.payment.model.PaymentRequest;
import com.bookmymovie.payment.model.PaymentResponseAck;
import com.bookmymovie.payment.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentConverter mockPaymentConverter;
    @Mock
    private PaymentGatewayProcessorService mockPaymentGatewayProcessorService;
    @Mock
    private PaymentRepository mockPaymentRepository;
    @Mock
    private StatusMapper mockStatusMapper;
    @Mock
    private RestTemplate mockRestTemplate;
    @Mock
    private MetricsContainerService mockMetricsContainerService;

    @InjectMocks
    private PaymentService paymentServiceUnderTest;

    @BeforeEach
    void setUp() {
        paymentServiceUnderTest.orderServiceAsyncUrl = "orderServiceAsyncUrl";
        paymentServiceUnderTest.paymentGatewayProcessorService = mockPaymentGatewayProcessorService;
        paymentServiceUnderTest.paymentRepository = mockPaymentRepository;
    }

    @Test
    void testCreatePayment() {
        // Setup
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .transactionId("transactionId")
                .payment(Payment.builder()
                        .paymentCategory("paymentCategory")
                        .finalAmount("finalAmount")
                        .build())
                .build();
        final PaymentResponseAck expectedResult = PaymentResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build();

        // Run the test
        final PaymentResponseAck result = paymentServiceUnderTest.createPayment(paymentRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapAckCode(PaymentResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build());
        verify(mockStatusMapper).mapSuccessCodeMsg(PaymentResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build());
    }
}
