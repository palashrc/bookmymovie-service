package com.bookmymovie.order.metrics;

import com.bookmymovie.order.constant.CommonConstants;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MetricsContainerService {

  private final String metricTagEnv;

  private final MeterRegistry meterRegistry;

  private final Counter counterOfOrderToPaymentErr;

  public MetricsContainerService(
      @Value("${bmm.metrics.tag.env}") String metricTagEnv, MeterRegistry meterRegistry) {
    this.metricTagEnv = metricTagEnv;
    this.meterRegistry = meterRegistry;
    this.counterOfOrderToPaymentErr =
            Counter.builder("bmm.order.http.ordertopayment.errcount")
                .description("Counts Exception while sending request from Order to Payment")
                .tag(CommonConstants.METRICS_ENV, metricTagEnv.trim())
                .register(meterRegistry);
  }

  public void incrementOfOrderToPaymentErrCountMetric() {
    counterOfOrderToPaymentErr.increment();
    log.error("Metrics Counter for Order to Payment Microservice Connectivity Error incremented...");
  }
}
