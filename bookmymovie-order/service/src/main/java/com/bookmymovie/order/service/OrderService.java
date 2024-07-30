package com.bookmymovie.order.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.TransactionNotFoundException;
import com.bookmymovie.order.constant.ExceptionConstants;
import com.bookmymovie.order.converter.OrderConverter;
import com.bookmymovie.order.helper.StatusMapper;
import com.bookmymovie.order.metrics.MetricsContainerService;
import com.bookmymovie.order.model.OrderRequest;
import com.bookmymovie.order.model.OrderResponseAck;
import com.bookmymovie.order.model.PaymentRequest;
import com.bookmymovie.order.model.PaymentResponseAck;
import com.bookmymovie.order.util.OrderUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

@Service
@Slf4j
public class OrderService {

    private String txnId;

    @Value("${convenience.fees.percentage}")
    private String convenienceFeesPercentage;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    MetricsContainerService metricsContainerService;

    public OrderResponseAck createOrder(OrderRequest orderRequest) {
        log.info("Order Received: " + orderRequest);
        OrderResponseAck ack = new OrderResponseAck();
        try {
            init(orderRequest, ack);
            BigDecimal totalAmount = OrderUtils.calculateTotalAmount(orderRequest);
            BigDecimal totalAmountAfterDiscounts = discountService.applyDiscounts(orderRequest, totalAmount);
            BigDecimal finalPayableAmount = OrderUtils.calculateConvenienceFees(totalAmountAfterDiscounts, new BigDecimal(convenienceFeesPercentage));
            PaymentRequest paymentRequest = orderConverter.convertOrderToPayment(orderRequest, finalPayableAmount);
            statusMapper.mapAckCode(ack);
            statusMapper.mapSuccessCodeMsg(ack);
            Thread thread = new Thread(new PaymentAsyncProcessServiceThread(paymentRequest));
            thread.start();
        } catch (TransactionNotFoundException ex) {
            log.error("TransactionNotFoundException Occurs!");
            ex.printStackTrace();
            ack.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.TXN_NOT_FOUND_EXCEPTION_TYPE));
        }  catch (CoversionException ex) {
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            ack.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            ack.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        } finally {
            end(txnId);
        }
        return ack;
    }

    private void init(OrderRequest orderRequest, OrderResponseAck ack) throws TransactionNotFoundException {
        if(StringUtils.isAllEmpty(orderRequest.getTransactionId())) {
            log.error("Transaction not found in Request!");
            throw new TransactionNotFoundException();
        }
        this.txnId = orderRequest.getTransactionId();
        ack.setTransactionId(txnId);
        //Save OrderRequestTracker in DB: TxnId,ReqTimeStamp,TxnStatus
    }

    private void end(String txnId) {
        //Update OrderRequestTracker in DB: TxnId,ResInterimTimeStamp,TxnStatus
    }

    class PaymentAsyncProcessServiceThread implements Runnable {
        PaymentRequest paymentRequest;
        public PaymentAsyncProcessServiceThread(PaymentRequest paymentRequest) { this.paymentRequest = paymentRequest; }
        @Override
        public void run() {
            log.info("Payment Processing Asynchronous Flow...");
            try {
                HttpEntity<PaymentRequest> paymentRequestHttpEntity = new HttpEntity<>(paymentRequest);
                ResponseEntity<PaymentResponseAck> paymentAckResponseEntity = restTemplate.exchange(paymentServiceUrl, HttpMethod.POST, paymentRequestHttpEntity, PaymentResponseAck.class);
                if (paymentAckResponseEntity.getStatusCode().is2xxSuccessful()) {
                    log.info("Payment Microservice Interim Response: " + paymentAckResponseEntity.getBody());
                } else {
                    log.error("Payment Microservice Interim Response Error!");
                }
            } catch(Exception ex) {
                log.error("Exception Occurs for Payment Microservice Interim Connectivity!");
                ex.printStackTrace();
                metricsContainerService.incrementOfOrderToPaymentErrCountMetric();
            }
        }
    }
}
