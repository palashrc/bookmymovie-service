package com.bookmymovie.order.service;

import com.bookmymovie.core.error.TransactionNotFoundException;
import com.bookmymovie.order.constant.ExceptionConstants;
import com.bookmymovie.order.converter.OrderConverter;
import com.bookmymovie.order.helper.StatusMapper;
import com.bookmymovie.order.model.OrderRequest;
import com.bookmymovie.order.model.OrderResponseAck;
import com.bookmymovie.order.model.payment.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    @Value("${payment.service.url}")
    String paymentServiceUrl;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private StatusMapper statusMapper;

    public OrderResponseAck createOrder(OrderRequest orderRequest) {
        OrderResponseAck ack = new OrderResponseAck();
        log.info("Order received..." + orderRequest);
        try {
            String txnId = init(orderRequest);
            ack.setTransactionId(txnId);
            PaymentRequest paymentRequest = orderConverter.convertOrderToPayment(orderRequest);
            statusMapper.mapAckCode(ack);
            statusMapper.mapSuccessCodeMsg(ack);
            Thread thread = new Thread(new PaymentAsyncProcessServiceThread(paymentRequest));
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

    private String init(OrderRequest orderRequest) throws TransactionNotFoundException {
        if(StringUtils.isAllEmpty(orderRequest.getTransactionId())) {
            log.error("Transaction not found in Request!");
            throw new TransactionNotFoundException();
        }
        //Save OrderRequestTracker in DB: TxnId,ReqTimeStamp,TxnStatus
        return orderRequest.getTransactionId();
    }

    private void end() {
        //Update OrderRequestTracker in DB: TxnId,ResInterimTimeStamp,TxnStatus
    }

    class PaymentAsyncProcessServiceThread implements Runnable {
        PaymentRequest paymentRequest;
        public PaymentAsyncProcessServiceThread(PaymentRequest paymentRequest) {
            this.paymentRequest = paymentRequest;
        }
        @Override
        public void run() {
            log.info("Payment Processing Asynchronous Flow...");
        }
    }

}
