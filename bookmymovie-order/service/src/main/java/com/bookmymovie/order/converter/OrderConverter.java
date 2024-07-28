package com.bookmymovie.order.converter;

import com.bookmymovie.core.util.CommonUtils;
import com.bookmymovie.order.model.*;
import com.bookmymovie.core.error.CoversionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@Slf4j
public class OrderConverter {

    public PaymentRequest convertOrderToPayment(OrderRequest orderRequest, BigDecimal finalAmount) throws CoversionException {
        if (orderRequest == null) {
            log.error("Order to Payment Conversion Failed!");
            throw new CoversionException();
        }
        if (orderRequest.getOrder() == null) {
            log.error("Booking to Order Conversion Failed!");
            throw new CoversionException();
        }
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setTransactionId(orderRequest.getTransactionId());

        com.bookmymovie.order.model.payment.Payment payment = new com.bookmymovie.order.model.payment.Payment();
        payment.setPaymentCategory(orderRequest.getOrder().getPayment().getPaymentCategory());
        payment.setFinalAmount(finalAmount);

        if(orderRequest.getOrder().getPayment().getPaymentCategory().equalsIgnoreCase(PaymentMode.UPI.toString())) {
            if(orderRequest.getOrder().getPayment().getUpi() != null) {
                UPI upi = new UPI();
                upi.setUpiId(orderRequest.getOrder().getPayment().getUpi().getUpiId());
                upi.setUpiName(orderRequest.getOrder().getPayment().getUpi().getUpiName());
                payment.setUpi(upi);
            }
        }
        if(orderRequest.getOrder().getPayment().getPaymentCategory().equalsIgnoreCase(PaymentMode.CREDITCARD.toString())) {
            if(orderRequest.getOrder().getPayment().getCreditCard() != null) {
                DebitCard debitCard = new DebitCard();
                debitCard.setBankName(orderRequest.getOrder().getPayment().getDebitCard().getBankName());
                debitCard.setCardNumber(orderRequest.getOrder().getPayment().getDebitCard().getCardNumber());
                debitCard.setCvv(orderRequest.getOrder().getPayment().getDebitCard().getCvv());
                debitCard.setExpiry(orderRequest.getOrder().getPayment().getDebitCard().getExpiry());
                debitCard.setNameOnCard(orderRequest.getOrder().getPayment().getDebitCard().getNameOnCard());
                payment.setDebitCard(debitCard);
            }
        }
        if(orderRequest.getOrder().getPayment().getPaymentCategory().equalsIgnoreCase(PaymentMode.DEBITCARD.toString())) {
            if(orderRequest.getOrder().getPayment().getDebitCard() != null) {
                DebitCard debitCard = new DebitCard();
                debitCard.setBankName(orderRequest.getOrder().getPayment().getDebitCard().getBankName());
                debitCard.setCardNumber(orderRequest.getOrder().getPayment().getDebitCard().getCardNumber());
                debitCard.setCvv(orderRequest.getOrder().getPayment().getDebitCard().getCvv());
                debitCard.setExpiry(orderRequest.getOrder().getPayment().getDebitCard().getExpiry());
                debitCard.setNameOnCard(orderRequest.getOrder().getPayment().getDebitCard().getNameOnCard());
                payment.setDebitCard(debitCard);
            }
        }
        if(orderRequest.getOrder().getPayment().getPaymentCategory().equalsIgnoreCase(PaymentMode.NETBANKING.toString())) {
            if(orderRequest.getOrder().getPayment().getNetBanking() != null) {
                NetBanking netBanking = new NetBanking();
                netBanking.setBankName(orderRequest.getOrder().getPayment().getNetBanking().getBankName());
                netBanking.setNetBankingId(orderRequest.getOrder().getPayment().getNetBanking().getNetBankingId());
                netBanking.setNetBankingPassword(orderRequest.getOrder().getPayment().getNetBanking().getNetBankingPassword());
                payment.setNetBanking(netBanking);
            }
        }

        paymentRequest.setPayment(payment);
        return paymentRequest;
    }

    public com.bookmymovie.order.entity.Order convertPaymentToOrderEntity(PaymentResponseAsync paymentResponseAsync, String orderId) {
        com.bookmymovie.order.entity.Order order = new com.bookmymovie.order.entity.Order();
        order.setOrderId(orderId);
        order.setTransactionId(paymentResponseAsync.getTransactionId());
        order.setPaymentId(paymentResponseAsync.getPaymentId());
        order.setPaymentCategory(paymentResponseAsync.getPaymentCategory());
        order.setFinalAmount(paymentResponseAsync.getFinalAmount());
        order.setOrderTimeStamp(CommonUtils.getTimeStamp());
        return order;
    }

    public OrderResponseAsync convertOrderEntityToOrderAsync(com.bookmymovie.order.entity.Order orderEntity, OrderResponseAsync async) {
        async.setTransactionId(orderEntity.getTransactionId());
        async.setOrderId(orderEntity.getOrderId());
        async.setPaymentConfirmation(Boolean.TRUE);
        async.setPaymentCategory(orderEntity.getPaymentCategory());
        async.setFinalAmount(orderEntity.getFinalAmount());
        async.setOrderTimeStamp(orderEntity.getOrderTimeStamp());
        return async;
    }

    enum PaymentMode {
        PAYLATER,
        DEBITCARD,
        CREDITCARD,
        UPI,
        NETBANKING
    }
}
