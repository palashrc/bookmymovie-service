package com.bookmymovie.orchestrator.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.Error;
import com.bookmymovie.orchestrator.constant.ExceptionConstants;
import com.bookmymovie.orchestrator.converter.BookingConverter;
import com.bookmymovie.orchestrator.helper.StatusMapper;
import com.bookmymovie.orchestrator.metrics.MetricsContainerService;
import com.bookmymovie.orchestrator.model.BookingRequest;
import com.bookmymovie.orchestrator.model.BookingResponseAck;
import com.bookmymovie.orchestrator.model.OrderRequest;
import com.bookmymovie.orchestrator.validation.BookingValidator;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrchestratorServiceTest {

    @Mock
    private BookingValidator mockBookingValidator;
    @Mock
    private BookingConverter mockBookingConverter;
    @Mock
    private StatusMapper mockStatusMapper;
    @Mock
    private OrchRequestTrackerService mockOrchRequestTrackerService;
    @Mock
    private RestTemplate mockRestTemplate;
    @Mock
    private MetricsContainerService mockMetricsContainerService;

    @InjectMocks
    private OrchestratorService orchestratorServiceUnderTest;

    @BeforeEach
    void setUp() {
        orchestratorServiceUnderTest.orderServiceUrl = "orderServiceUrl";
    }

    @Test
    void testCreateBooking() {
        // Setup
        final BookingRequest bookingRequest = BookingRequest.builder().build();
        final BookingResponseAck expectedResult = BookingResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockBookingValidator.validateBooking(BookingRequest.builder().build())).thenReturn(false);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.BOOKING_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final BookingResponseAck result = orchestratorServiceUnderTest.createBooking(bookingRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockOrchRequestTrackerService).createTracker("txnId");
        verify(mockMetricsContainerService).incrementOfBookingErrCountMetric();
        verify(mockOrchRequestTrackerService).updateTrackerForInterimRes("txnId", BookingResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testCreateBooking_BookingValidatorReturnsTrue() throws Exception {
        // Setup
        final BookingRequest bookingRequest = BookingRequest.builder().build();
        final BookingResponseAck expectedResult = BookingResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockBookingValidator.validateBooking(BookingRequest.builder().build())).thenReturn(true);
        when(mockBookingConverter.convertBookingToOrder(BookingRequest.builder().build(), "txnId"))
                .thenReturn(OrderRequest.builder().build());

        // Run the test
        final BookingResponseAck result = orchestratorServiceUnderTest.createBooking(bookingRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockOrchRequestTrackerService).createTracker("txnId");
        verify(mockStatusMapper).mapAckCode(BookingResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build());
        verify(mockStatusMapper).mapSuccessCodeMsg(BookingResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build());
        verify(mockOrchRequestTrackerService).updateTrackerForInterimRes("txnId", BookingResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testCreateBooking_BookingConverterThrowsCoversionException() throws Exception {
        // Setup
        final BookingRequest bookingRequest = BookingRequest.builder().build();
        final BookingResponseAck expectedResult = BookingResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockBookingValidator.validateBooking(BookingRequest.builder().build())).thenReturn(true);
        when(mockBookingConverter.convertBookingToOrder(BookingRequest.builder().build(), "txnId"))
                .thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.BOOKING_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final BookingResponseAck result = orchestratorServiceUnderTest.createBooking(bookingRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockOrchRequestTrackerService).createTracker("txnId");
        verify(mockOrchRequestTrackerService).updateTrackerForInterimRes("txnId", BookingResponseAck.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build());
    }
}
