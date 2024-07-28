package com.bookmymovie.payment.converter;

import com.bookmymovie.core.util.CommonUtils;
import com.bookmymovie.payment.model.PaymentRequest;
import com.bookmymovie.payment.model.PaymentResponseAsync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentConverter {

    public com.bookmymovie.payment.entity.Payment paymentToPaymentEntity(PaymentRequest paymentRequest) {
        com.bookmymovie.payment.entity.Payment paymentEntity = new com.bookmymovie.payment.entity.Payment();
        paymentEntity.setTransactionId(paymentRequest.getTransactionId());
        paymentEntity.setPaymentCategory(paymentRequest.getPayment().getPaymentCategory());
        paymentEntity.setFinalAmount(paymentRequest.getPayment().getFinalAmount());
        paymentEntity.setPaymentTimeStamp(CommonUtils.getTimeStamp());
        return paymentEntity;
    }

    public PaymentResponseAsync paymentEntityToPaymentAsync(com.bookmymovie.payment.entity.Payment paymentEntity, PaymentResponseAsync async) {
        async.setPaymentId(paymentEntity.getPaymentId());
        async.setTransactionId(paymentEntity.getTransactionId());
        async.setPaymentCategory(paymentEntity.getPaymentCategory());
        async.setFinalAmount(paymentEntity.getFinalAmount());
        async.setPaymentTimeStamp(paymentEntity.getPaymentTimeStamp());
        return async;
    }
}
