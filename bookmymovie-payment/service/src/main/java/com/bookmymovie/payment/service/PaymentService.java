package com.bookmymovie.payment.service;

import com.bookmymovie.core.error.TransactionNotFoundException;
import com.bookmymovie.payment.constant.ExceptionConstants;
import com.bookmymovie.payment.helper.StatusMapper;
import com.bookmymovie.payment.model.PaymentRequest;
import com.bookmymovie.payment.model.PaymentResponseAck;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    PaymentGatewayProcessorService paymentGatewayProcessorService;

    @Autowired
    private StatusMapper statusMapper;

    public PaymentResponseAck createPayment(PaymentRequest paymentRequest) {
        PaymentResponseAck ack = new PaymentResponseAck();
        log.info("Payment received: " + paymentRequest);

        try {
            String txnId = init(paymentRequest);
            ack.setTransactionId(txnId);
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

    private String init(PaymentRequest paymentRequest) throws TransactionNotFoundException {
        if(StringUtils.isAllEmpty(paymentRequest.getTransactionId())) {
            log.error("Transaction not found in Request!");
            throw new TransactionNotFoundException();
        }
        //Save PaymentRequestTracker in DB: TxnId,ReqTimeStamp,TxnStatus
        return paymentRequest.getTransactionId();
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
            //save payment DB.
            //get payment id.
            //connect order service..
            try {
                Boolean confirmation = paymentGatewayProcessorService.connectPaymentGateway();
            } catch(Exception ex) {
                log.error("Exception Occurs for Payment Gateway Connectivity!");
                ex.printStackTrace();
            }
        }
    }
}
