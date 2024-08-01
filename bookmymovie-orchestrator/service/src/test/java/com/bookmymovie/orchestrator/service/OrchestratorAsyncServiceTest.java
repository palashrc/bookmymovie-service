package com.bookmymovie.orchestrator.service;

import com.bookmymovie.orchestrator.model.OrderResponseAsync;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrchestratorAsyncServiceTest {

    @Mock
    private OrchRequestTrackerService mockOrchRequestTrackerService;

    @InjectMocks
    private OrchestratorAsyncService orchestratorAsyncServiceUnderTest;

    @Test
    void testCreateAsyncBooking() {
        // Setup
        final OrderResponseAsync async = OrderResponseAsync.builder().build();
        final OrderResponseAsync expectedResult = OrderResponseAsync.builder().build();

        // Run the test
        final OrderResponseAsync result = orchestratorAsyncServiceUnderTest.createAsyncBooking(async);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockOrchRequestTrackerService).updateTrackerForFinalRes(OrderResponseAsync.builder().build());
    }
}
