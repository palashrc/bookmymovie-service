package com.bookmymovie.order.metrics;

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
    void testIncrementOfOrderToPaymentErrCountMetric() {
        // Setup
        // Run the test
        metricsContainerServiceUnderTest.incrementOfOrderToPaymentErrCountMetric();

        // Verify the results
    }

    @Test
    void testIncrementOfOrderToOrchErrCountMetric() {
        // Setup
        // Run the test
        metricsContainerServiceUnderTest.incrementOfOrderToOrchErrCountMetric();

        // Verify the results
    }
}
