package com.bookmymovie.payment.service;

import com.bookmymovie.core.error.PaymentProcessException;
import com.bookmymovie.core.error.TransactionNotFoundException;
import com.bookmymovie.payment.constant.ExceptionConstants;
import com.bookmymovie.payment.converter.PaymentConverter;
import com.bookmymovie.payment.helper.StatusMapper;
import com.bookmymovie.payment.metrics.MetricsContainerService;
import com.bookmymovie.payment.model.PaymentRequest;
import com.bookmymovie.payment.model.PaymentResponseAck;
import com.bookmymovie.payment.model.PaymentResponseAsync;
import com.bookmymovie.payment.repository.PaymentRepository;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PaymentService {

    private String txnId;

    @Value("${order.service.async.url}")
    String orderServiceAsyncUrl;

    @Autowired
    private PaymentConverter paymentConverter;

    @Autowired
    PaymentGatewayProcessorService paymentGatewayProcessorService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MetricsContainerService metricsContainerService;

    public PaymentResponseAck createPayment(PaymentRequest paymentRequest) {
        PaymentResponseAck ack = new PaymentResponseAck();
        log.info("Payment Received: " + paymentRequest);
        try {
            init(paymentRequest, ack);
            statusMapper.mapAckCode(ack);
            statusMapper.mapSuccessCodeMsg(ack);
            Thread thread = new Thread(new PaymentGatewayAsyncProcessServiceThread(paymentRequest));
            thread.start();
        } catch (TransactionNotFoundException ex) {
            log.error("TransactionNotFoundException Occurs!");
            ex.printStackTrace();
            ack.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.TXN_NOT_FOUND_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            ack.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        } finally {
            end();
        }

        return ack;
    }

    private void init(PaymentRequest paymentRequest, PaymentResponseAck ack) throws TransactionNotFoundException {
        if(StringUtils.isAllEmpty(paymentRequest.getTransactionId())) {
            log.error("Transaction not found in Request!");
            throw new TransactionNotFoundException();
        }
        this.txnId = paymentRequest.getTransactionId();
        ack.setTransactionId(txnId);
        //Save PaymentRequestTracker in DB: TxnId,ReqTimeStamp,TxnStatus
    }

    private void end() {
        //Update PaymentRequestTracker in DB: TxnId,ResInterimTimeStamp,TxnStatus
    }

    class PaymentGatewayAsyncProcessServiceThread implements Runnable {
        PaymentRequest paymentRequest;
        public PaymentGatewayAsyncProcessServiceThread(PaymentRequest paymentRequest) { this.paymentRequest = paymentRequest; }
        @Override
        public void run() {
            log.info("Payment Gateway connecting Asynchronous Flow...");
            PaymentResponseAsync async = new PaymentResponseAsync();
            try {
                Boolean confirmation = paymentGatewayProcessorService.connectPaymentGateway();
                if (!confirmation) {
                    log.error("Payment Processing Failed!");
                    throw new PaymentProcessException();
                }
                com.bookmymovie.payment.entity.Payment paymentEntityRes = paymentRepository.save(paymentConverter.paymentToPaymentEntity(paymentRequest));
                statusMapper.mapSuccessCodeMsg(paymentConverter.paymentEntityToPaymentAsync(paymentEntityRes, async));
            } catch(PaymentProcessException ex) {
                log.error("PaymentProcessException Occurs!");
                ex.printStackTrace();
                async.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.PAYMENT_PROCESS_EXCEPTION_TYPE));
                populatePaymentErrorResponse(paymentRequest, async);
            } catch(DatastoreException ex) {
                log.error("DatastoreException Occurs!");
                ex.printStackTrace();
                async.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
                populatePaymentErrorResponse(paymentRequest, async);
            } catch(Exception ex) {
                log.error("Exception Occurs!");
                ex.printStackTrace();
                async.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
                populatePaymentErrorResponse(paymentRequest, async);
            } finally {
                log.info("Sending Asynchronous Response to Order Service...");
                try {
                    HttpEntity<PaymentResponseAsync> orderRequestHttpEntity = new HttpEntity<>(async);
                    restTemplate.exchange(orderServiceAsyncUrl, HttpMethod.POST, orderRequestHttpEntity, PaymentResponseAsync.class);
                } catch(Exception ex) {
                    log.error("Exception Occurs for Order Microservice Async Connectivity!");
                    ex.printStackTrace();
                    metricsContainerService.incrementOfPaymentToOrderErrCountMetric();
                }
            }
        }

        private void populatePaymentErrorResponse(PaymentRequest paymentRequest, PaymentResponseAsync async) {
            async.setTransactionId(paymentRequest.getTransactionId());
            async.setPaymentCategory(paymentRequest.getPayment().getPaymentCategory());
            async.setFinalAmount(paymentRequest.getPayment().getFinalAmount());
        }
    }
}
