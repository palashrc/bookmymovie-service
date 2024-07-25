package com.bookmymovie.order.service;

import com.bookmymovie.core.error.PaymentProcessException;
import com.bookmymovie.core.util.CommonUtils;
import com.bookmymovie.order.constant.CommonConstants;
import com.bookmymovie.order.constant.ExceptionConstants;
import com.bookmymovie.order.helper.StatusMapper;
import com.bookmymovie.order.model.OrderResponseAsync;
import com.bookmymovie.order.model.PaymentResponseAsync;
import com.bookmymovie.order.repository.OrderRepository;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Service
@Slf4j
public class OrderAsyncService {

    @Value("${orch.service.async.url}")
    String orchServiceAsyncUrl;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private RestTemplate restTemplate;

    public PaymentResponseAsync processAsyncOrder(PaymentResponseAsync paymentResponseAsync) {
        log.info("Payment Microservice Asynchronous Response: " + paymentResponseAsync);
        OrderResponseAsync async = new OrderResponseAsync();
        try {
            if (ObjectUtils.isEmpty(paymentResponseAsync.getPaymentId())) {
                log.error("Payment Processing Failed!");
                throw new PaymentProcessException();
            }
            String orderId = init();
            com.bookmymovie.order.entity.Order order = new com.bookmymovie.order.entity.Order();
            order.setOrderId(orderId);
            order.setTransactionId(paymentResponseAsync.getTransactionId());
            order.setPaymentId(paymentResponseAsync.getPaymentId());
            order.setPaymentCategory(paymentResponseAsync.getPaymentCategory());
            order.setFinalAmount(paymentResponseAsync.getFinalAmount());
            order.setOrderTimeStamp(CommonUtils.getTimeStamp());
            com.bookmymovie.order.entity.Order orderEntityRes = orderRepository.save(order);

            async.setTransactionId(orderEntityRes.getTransactionId());
            async.setOrderId(orderEntityRes.getOrderId());
            async.setPaymentConfirmation(Boolean.TRUE);
            async.setPaymentCategory(orderEntityRes.getPaymentCategory());
            async.setFinalAmount(orderEntityRes.getFinalAmount());
            async.setOrderTimeStamp(orderEntityRes.getOrderTimeStamp());

            statusMapper.mapSuccessCodeMsg(async);

        } catch(PaymentProcessException ex) {
            log.error("PaymentProcessException Occurs!");
            ex.printStackTrace();
            async.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.PAYMENT_PROCESS_EXCEPTION_TYPE));
            populateOrderErrorResponse(paymentResponseAsync, async);
        } catch (DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            async.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
            populateOrderErrorResponse(paymentResponseAsync, async);
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            async.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
            populateOrderErrorResponse(paymentResponseAsync, async);
        } finally {
            end();
            log.info("Sending Asynchronous Response to Orchestrator Service...");
            try {
                HttpEntity<OrderResponseAsync> orchRequestHttpEntity = new HttpEntity<>(async);
                restTemplate.exchange(orchServiceAsyncUrl, HttpMethod.POST, orchRequestHttpEntity, OrderResponseAsync.class);
            } catch(Exception ex) {
                log.error("Exception Occurs for Orchestrator Microservice Async Connectivity!");
                ex.printStackTrace();
            }
        }
        return paymentResponseAsync;
    }

    private String init() {
        UUID uuid = UUID.randomUUID();
        String orderId = CommonConstants.ORDER_PREFIX + uuid.toString().replaceAll("-","");
        return orderId;
    }

    private void end() {
    }

    private void populateOrderErrorResponse(PaymentResponseAsync paymentResponseAsync, OrderResponseAsync async) {
        async.setTransactionId(paymentResponseAsync.getTransactionId());
        async.setPaymentConfirmation(Boolean.FALSE);
    }
}
