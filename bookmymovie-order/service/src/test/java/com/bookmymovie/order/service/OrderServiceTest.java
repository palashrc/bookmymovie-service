package com.bookmymovie.order.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.Error;
import com.bookmymovie.order.constant.ExceptionConstants;
import com.bookmymovie.order.converter.OrderConverter;
import com.bookmymovie.order.helper.StatusMapper;
import com.bookmymovie.order.metrics.MetricsContainerService;
import com.bookmymovie.order.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private DiscountService mockDiscountService;
    @Mock
    private OrderConverter mockOrderConverter;
    @Mock
    private StatusMapper mockStatusMapper;
    @Mock
    private RestTemplate mockRestTemplate;
    @Mock
    private MetricsContainerService mockMetricsContainerService;

    @InjectMocks
    private OrderService orderServiceUnderTest;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(orderServiceUnderTest, "convenienceFeesPercentage", "seatPrice");
        ReflectionTestUtils.setField(orderServiceUnderTest, "paymentServiceUrl", "paymentServiceUrl");
        orderServiceUnderTest.metricsContainerService = mockMetricsContainerService;
    }

    @Test
    void testCreateOrder() throws Exception {
        // Setup
        final OrderRequest orderRequest = OrderRequest.builder()
                .transactionId("transactionId")
                .order(Order.builder()
                        .seatBook(List.of(SeatBook.builder()
                                .seatPrice("seatPrice")
                                .build()))
                        .build())
                .build();
        final OrderResponseAck expectedResult = OrderResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockDiscountService.applyDiscounts(OrderRequest.builder()
                .transactionId("transactionId")
                .order(Order.builder()
                        .seatBook(List.of(SeatBook.builder()
                                .seatPrice("seatPrice")
                                .build()))
                        .build())
                .build(), new BigDecimal("0.00"))).thenReturn(new BigDecimal("0.00"));
        when(mockOrderConverter.convertOrderToPayment(OrderRequest.builder()
                .transactionId("transactionId")
                .order(Order.builder()
                        .seatBook(List.of(SeatBook.builder()
                                .seatPrice("seatPrice")
                                .build()))
                        .build())
                .build(), new BigDecimal("0.00"))).thenReturn(PaymentRequest.builder().build());

        // Run the test
        final OrderResponseAck result = orderServiceUnderTest.createOrder(orderRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapAckCode(OrderResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build());
        verify(mockStatusMapper).mapSuccessCodeMsg(OrderResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testCreateOrder_OrderConverterThrowsCoversionException() throws Exception {
        // Setup
        final OrderRequest orderRequest = OrderRequest.builder()
                .transactionId("transactionId")
                .order(Order.builder()
                        .seatBook(List.of(SeatBook.builder()
                                .seatPrice("seatPrice")
                                .build()))
                        .build())
                .build();
        final OrderResponseAck expectedResult = OrderResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockDiscountService.applyDiscounts(OrderRequest.builder()
                .transactionId("transactionId")
                .order(Order.builder()
                        .seatBook(List.of(SeatBook.builder()
                                .seatPrice("seatPrice")
                                .build()))
                        .build())
                .build(), new BigDecimal("0.00"))).thenReturn(new BigDecimal("0.00"));
        when(mockOrderConverter.convertOrderToPayment(OrderRequest.builder()
                .transactionId("transactionId")
                .order(Order.builder()
                        .seatBook(List.of(SeatBook.builder()
                                .seatPrice("seatPrice")
                                .build()))
                        .build())
                .build(), new BigDecimal("0.00"))).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.TXN_NOT_FOUND_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final OrderResponseAck result = orderServiceUnderTest.createOrder(orderRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
