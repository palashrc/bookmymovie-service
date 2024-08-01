package com.bookmymovie.orchestrator.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MetricsContainerServiceTest {

    @Mock
    private MeterRegistry mockMeterRegistry;

    private MetricsContainerService metricsContainerServiceUnderTest;

    @BeforeEach
    void setUp() {
        metricsContainerServiceUnderTest = new MetricsContainerService("metricTagEnv", mockMeterRegistry);
    }

    @Test
    void testIncrementOfOrchToOrderErrCountMetric() {
        // Setup
        // Run the test
        metricsContainerServiceUnderTest.incrementOfOrchToOrderErrCountMetric();

        // Verify the results
    }

    @Test
    void testIncrementOfBookingErrCountMetric() {
        // Setup
        // Run the test
        metricsContainerServiceUnderTest.incrementOfBookingErrCountMetric();

        // Verify the results
    }
}
