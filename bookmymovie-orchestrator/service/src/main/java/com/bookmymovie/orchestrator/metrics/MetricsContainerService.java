package com.bookmymovie.orchestrator.metrics;

import com.bookmymovie.orchestrator.constant.CommonConstants;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MetricsContainerService {

  private final String metricTagEnv;

  private final MeterRegistry meterRegistry;

  private final Counter counterOfOrchToOrderErr;

  private final Counter counterOfBookingErr;

  public MetricsContainerService(
      @Value("${bmm.metrics.tag.env}") String metricTagEnv, MeterRegistry meterRegistry) {
    this.metricTagEnv = metricTagEnv;
    this.meterRegistry = meterRegistry;
    this.counterOfOrchToOrderErr =
            Counter.builder("bmm.orch.http.orchtoorder.errcount")
                .description("Counts Exception while sending request from Orchestrator to Order")
                .tag(CommonConstants.METRICS_ENV, metricTagEnv.trim())
                .register(meterRegistry);
    this.counterOfBookingErr =
            Counter.builder("bmm.orch.http.booking.errcount")
                    .description("Counts Exception while Booking")
                    .tag(CommonConstants.METRICS_ENV, metricTagEnv.trim())
                    .register(meterRegistry);
  }

  public void incrementOfOrchToOrderErrCountMetric() {
    counterOfOrchToOrderErr.increment();
    log.error("Metrics Counter for Orchestrator to Order Microservice Connectivity Error incremented...");
  }

  public void incrementOfBookingErrCountMetric() {
    counterOfBookingErr.increment();
    log.error("Metrics Counter for Booking Error incremented...");
  }
}
