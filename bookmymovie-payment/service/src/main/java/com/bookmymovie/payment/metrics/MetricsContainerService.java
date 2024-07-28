package com.bookmymovie.payment.metrics;

import com.bookmymovie.payment.constant.CommonConstants;
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

  private final Counter counterOfPaymentToOrderErr;

  public MetricsContainerService(
      @Value("${bmm.metrics.tag.env}") String metricTagEnv, MeterRegistry meterRegistry) {
    this.metricTagEnv = metricTagEnv;
    this.meterRegistry = meterRegistry;
    this.counterOfPaymentToOrderErr =
            Counter.builder("bmm.payment.http.paymenttoorder.errcount")
                .description("Counts Exception while sending response from Payment to Order")
                .tag(CommonConstants.METRICS_ENV, metricTagEnv.trim())
                .register(meterRegistry);
  }

  public void incrementOfPaymentToOrderErrCountMetric() {
    counterOfPaymentToOrderErr.increment();
    log.error("Metrics Counter for Payment to Order Microservice Connectivity Error incremented...");
  }
}
