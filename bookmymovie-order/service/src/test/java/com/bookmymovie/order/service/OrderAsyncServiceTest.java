package com.bookmymovie.order.service;

import com.bookmymovie.core.error.Error;
import com.bookmymovie.order.converter.OrderConverter;
import com.bookmymovie.order.entity.Order;
import com.bookmymovie.order.helper.StatusMapper;
import com.bookmymovie.order.metrics.MetricsContainerService;
import com.bookmymovie.order.model.OrderResponseAsync;
import com.bookmymovie.order.model.PaymentResponseAsync;
import com.bookmymovie.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderAsyncServiceTest {

    @Mock
    private OrderRepository mockOrderRepository;
    @Mock
    private OrderConverter mockOrderConverter;
    @Mock
    private StatusMapper mockStatusMapper;
    @Mock
    private RestTemplate mockRestTemplate;
    @Mock
    private MetricsContainerService mockMetricsContainerService;

    @InjectMocks
    private OrderAsyncService orderAsyncServiceUnderTest;

    @BeforeEach
    void setUp() {
        orderAsyncServiceUnderTest.orchServiceAsyncUrl = "orchServiceAsyncUrl";
    }

    @Test
    void testProcessAsyncOrder() {
        // Setup
        final PaymentResponseAsync paymentResponseAsync = PaymentResponseAsync.builder()
                .transactionId("transactionId")
                .paymentId(0L)
                .build();
        final PaymentResponseAsync expectedResult = PaymentResponseAsync.builder()
                .transactionId("transactionId")
                .paymentId(0L)
                .build();
        when(mockOrderConverter.convertPaymentToOrderEntity(PaymentResponseAsync.builder()
                .transactionId("transactionId")
                .paymentId(0L)
                .build(), "orderId")).thenReturn(Order.builder().build());
        when(mockOrderRepository.save(Order.builder().build())).thenReturn(Order.builder().build());

        // Configure OrderConverter.convertOrderEntityToOrderAsync(...).
        final OrderResponseAsync orderResponseAsync = OrderResponseAsync.builder()
                .transactionId("transactionId")
                .paymentConfirmation(false)
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockOrderConverter.convertOrderEntityToOrderAsync(Order.builder().build(), OrderResponseAsync.builder()
                .transactionId("transactionId")
                .paymentConfirmation(false)
                .errors(List.of(Error.builder().build()))
                .build())).thenReturn(orderResponseAsync);

        // Run the test
        final PaymentResponseAsync result = orderAsyncServiceUnderTest.processAsyncOrder(paymentResponseAsync);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(OrderResponseAsync.builder()
                .transactionId("transactionId")
                .paymentConfirmation(false)
                .errors(List.of(Error.builder().build()))
                .build());
        verify(mockRestTemplate).exchange("orchServiceAsyncUrl", HttpMethod.POST,
                new HttpEntity<>(OrderResponseAsync.builder()
                        .transactionId("transactionId")
                        .paymentConfirmation(false)
                        .errors(List.of(Error.builder().build()))
                        .build(), new HttpHeaders()), OrderResponseAsync.class);
    }

    @Test
    void testProcessAsyncOrder_RestTemplateThrowsRestClientException() {
        // Setup
        final PaymentResponseAsync paymentResponseAsync = PaymentResponseAsync.builder()
                .transactionId("transactionId")
                .paymentId(0L)
                .build();
        final PaymentResponseAsync expectedResult = PaymentResponseAsync.builder()
                .transactionId("transactionId")
                .paymentId(0L)
                .build();
        when(mockOrderConverter.convertPaymentToOrderEntity(PaymentResponseAsync.builder()
                .transactionId("transactionId")
                .paymentId(0L)
                .build(), "orderId")).thenReturn(Order.builder().build());
        when(mockOrderRepository.save(Order.builder().build())).thenReturn(Order.builder().build());

        // Configure OrderConverter.convertOrderEntityToOrderAsync(...).
        final OrderResponseAsync orderResponseAsync = OrderResponseAsync.builder()
                .transactionId("transactionId")
                .paymentConfirmation(false)
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockOrderConverter.convertOrderEntityToOrderAsync(Order.builder().build(), OrderResponseAsync.builder()
                .transactionId("transactionId")
                .paymentConfirmation(false)
                .errors(List.of(Error.builder().build()))
                .build())).thenReturn(orderResponseAsync);

        when(mockRestTemplate.exchange("orchServiceAsyncUrl", HttpMethod.POST,
                new HttpEntity<>(OrderResponseAsync.builder()
                        .transactionId("transactionId")
                        .paymentConfirmation(false)
                        .errors(List.of(Error.builder().build()))
                        .build(), new HttpHeaders()), OrderResponseAsync.class)).thenThrow(RestClientException.class);

        // Run the test
        final PaymentResponseAsync result = orderAsyncServiceUnderTest.processAsyncOrder(paymentResponseAsync);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(OrderResponseAsync.builder()
                .transactionId("transactionId")
                .paymentConfirmation(false)
                .errors(List.of(Error.builder().build()))
                .build());
        verify(mockMetricsContainerService).incrementOfOrderToOrchErrCountMetric();
    }
}
