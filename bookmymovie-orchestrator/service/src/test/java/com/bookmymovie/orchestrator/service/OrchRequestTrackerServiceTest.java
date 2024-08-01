package com.bookmymovie.orchestrator.service;

import com.bookmymovie.core.error.Error;
import com.bookmymovie.orchestrator.entity.OrchRequestTracker;
import com.bookmymovie.orchestrator.model.BookingResponseAck;
import com.bookmymovie.orchestrator.model.OrderResponseAsync;
import com.bookmymovie.orchestrator.repository.OrchRequestTrackerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrchRequestTrackerServiceTest {

    @Mock
    private OrchRequestTrackerRepository mockOrchRequestTrackerRepository;

    @InjectMocks
    private OrchRequestTrackerService orchRequestTrackerServiceUnderTest;

    @Test
    void testCreateTracker() {
        // Setup
        // Run the test
        orchRequestTrackerServiceUnderTest.createTracker("txnId");

        // Verify the results
        verify(mockOrchRequestTrackerRepository).save(OrchRequestTracker.builder()
                .transactionId("txnId")
                .reqTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .resInterimTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .resFinalTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .txnStatus("txnStatus")
                .build());
    }

    @Test
    void testUpdateTrackerForInterimRes() {
        // Setup
        final BookingResponseAck ack = BookingResponseAck.builder()
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure OrchRequestTrackerRepository.findByTransactionId(...).
        final Optional<OrchRequestTracker> orchRequestTracker = Optional.of(OrchRequestTracker.builder()
                .transactionId("txnId")
                .reqTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .resInterimTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .resFinalTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .txnStatus("txnStatus")
                .build());
        when(mockOrchRequestTrackerRepository.findByTransactionId("txnId")).thenReturn(orchRequestTracker);

        // Run the test
        orchRequestTrackerServiceUnderTest.updateTrackerForInterimRes("txnId", ack);

        // Verify the results
        verify(mockOrchRequestTrackerRepository).save(OrchRequestTracker.builder()
                .transactionId("txnId")
                .reqTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .resInterimTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .resFinalTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .txnStatus("txnStatus")
                .build());
    }

    @Test
    void testUpdateTrackerForInterimRes_OrchRequestTrackerRepositoryFindByTransactionIdReturnsAbsent() {
        // Setup
        final BookingResponseAck ack = BookingResponseAck.builder()
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockOrchRequestTrackerRepository.findByTransactionId("txnId")).thenReturn(Optional.empty());

        // Run the test
        orchRequestTrackerServiceUnderTest.updateTrackerForInterimRes("txnId", ack);

        // Verify the results
    }

    @Test
    void testUpdateTrackerForFinalRes() {
        // Setup
        final OrderResponseAsync async = OrderResponseAsync.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure OrchRequestTrackerRepository.findByTransactionId(...).
        final Optional<OrchRequestTracker> orchRequestTracker = Optional.of(OrchRequestTracker.builder()
                .transactionId("txnId")
                .reqTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .resInterimTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .resFinalTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .txnStatus("txnStatus")
                .build());
        when(mockOrchRequestTrackerRepository.findByTransactionId("transactionId")).thenReturn(orchRequestTracker);

        // Run the test
        orchRequestTrackerServiceUnderTest.updateTrackerForFinalRes(async);

        // Verify the results
        verify(mockOrchRequestTrackerRepository).save(OrchRequestTracker.builder()
                .transactionId("txnId")
                .reqTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .resInterimTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .resFinalTimeStamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .txnStatus("txnStatus")
                .build());
    }

    @Test
    void testUpdateTrackerForFinalRes_OrchRequestTrackerRepositoryFindByTransactionIdReturnsAbsent() {
        // Setup
        final OrderResponseAsync async = OrderResponseAsync.builder()
                .transactionId("transactionId")
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockOrchRequestTrackerRepository.findByTransactionId("transactionId")).thenReturn(Optional.empty());

        // Run the test
        orchRequestTrackerServiceUnderTest.updateTrackerForFinalRes(async);

        // Verify the results
    }
}
