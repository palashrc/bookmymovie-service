package com.bookmymovie.orchestrator.converter;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.orchestrator.model.*;
import com.bookmymovie.orchestrator.model.Order;
import com.bookmymovie.orchestrator.model.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookingConverter {

    public OrderRequest convertBookingToOrder(BookingRequest bookingRequest, String txnId) throws CoversionException {
        if (bookingRequest == null) {
            log.error("Booking to Order Conversion Failed!");
            throw new CoversionException();
        }
        if (bookingRequest.getBooking() == null) {
            log.error("Booking to Order Conversion Failed!");
            throw new CoversionException();
        }
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setTransactionId(txnId);
        Order order = new Order();
        order.setBookingId(bookingRequest.getBooking().getBookingId());
        order.setViewerId(bookingRequest.getBooking().getViewerId());
        order.setFirstName(bookingRequest.getBooking().getFirstName());
        order.setLastName(bookingRequest.getBooking().getLastName());
        order.setMobile(bookingRequest.getBooking().getMobile());
        order.setEmail(bookingRequest.getBooking().getEmail());
        order.setMovieId(bookingRequest.getBooking().getMovieId());
        order.setMovieName(bookingRequest.getBooking().getMovieName());
        order.setMovieCertificate(bookingRequest.getBooking().getMovieCertificate());
        order.setTheaterId(bookingRequest.getBooking().getTheaterId());
        order.setTheaterName(bookingRequest.getBooking().getTheaterName());
        order.setScreenId(bookingRequest.getBooking().getScreenId());
        order.setScreenName(bookingRequest.getBooking().getScreenName());
        order.setShowdate(bookingRequest.getBooking().getShowdate());
        order.setSeatBook(bookingRequest.getBooking().getSeatBook());

        Payment payment = new Payment();
        payment.setPaymentCategory(bookingRequest.getBooking().getPayment().getPaymentCategory());

        if(bookingRequest.getBooking().getPayment().getPaymentCategory().equalsIgnoreCase(PaymentMode.UPI.toString())) {
            if(bookingRequest.getBooking().getPayment().getUpi() != null) {
                UPI upi = new UPI();
                upi.setUpiId(bookingRequest.getBooking().getPayment().getUpi().getUpiId());
                upi.setUpiName(bookingRequest.getBooking().getPayment().getUpi().getUpiName());
                payment.setUpi(upi);
            }
        }
        if(bookingRequest.getBooking().getPayment().getPaymentCategory().equalsIgnoreCase(PaymentMode.CREDITCARD.toString())) {
            if(bookingRequest.getBooking().getPayment().getCreditCard() != null) {
                CreditCard creditCard = new CreditCard();
                creditCard.setBankName(bookingRequest.getBooking().getPayment().getCreditCard().getBankName());
                creditCard.setCardNumber(bookingRequest.getBooking().getPayment().getCreditCard().getCardNumber());
                creditCard.setCvv(bookingRequest.getBooking().getPayment().getCreditCard().getCvv());
                creditCard.setExpiry(bookingRequest.getBooking().getPayment().getCreditCard().getExpiry());
                creditCard.setNameOnCard(bookingRequest.getBooking().getPayment().getCreditCard().getNameOnCard());
                payment.setCreditCard(creditCard);
            }
        }
        if(bookingRequest.getBooking().getPayment().getPaymentCategory().equalsIgnoreCase(PaymentMode.DEBITCARD.toString())) {
            if(bookingRequest.getBooking().getPayment().getDebitCard() != null) {
                DebitCard debitCard = new DebitCard();
                debitCard.setBankName(bookingRequest.getBooking().getPayment().getDebitCard().getBankName());
                debitCard.setCardNumber(bookingRequest.getBooking().getPayment().getDebitCard().getCardNumber());
                debitCard.setCvv(bookingRequest.getBooking().getPayment().getDebitCard().getCvv());
                debitCard.setExpiry(bookingRequest.getBooking().getPayment().getDebitCard().getExpiry());
                debitCard.setNameOnCard(bookingRequest.getBooking().getPayment().getDebitCard().getNameOnCard());
                payment.setDebitCard(debitCard);
            }
        }
        if(bookingRequest.getBooking().getPayment().getPaymentCategory().equalsIgnoreCase(PaymentMode.NETBANKING.toString())) {
            if(bookingRequest.getBooking().getPayment().getNetBanking() != null) {
                NetBanking netBanking = new NetBanking();
                netBanking.setBankName(bookingRequest.getBooking().getPayment().getNetBanking().getBankName());
                netBanking.setNetBankingId(bookingRequest.getBooking().getPayment().getNetBanking().getNetBankingId());
                netBanking.setNetBankingPassword(bookingRequest.getBooking().getPayment().getNetBanking().getNetBankingPassword());
                payment.setNetBanking(netBanking);
            }
        }

        order.setPayment(payment);
        orderRequest.setOrder(order);
        return orderRequest;
    }

    enum PaymentMode {
        PAYLATER,
        DEBITCARD,
        CREDITCARD,
        UPI,
        NETBANKING
    }
}
