package com.bookmymovie.payment.metrics;

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
    void testIncrementOfPaymentToOrderErrCountMetric() {
        // Setup
        // Run the test
        metricsContainerServiceUnderTest.incrementOfPaymentToOrderErrCountMetric();

        // Verify the results
    }
}
