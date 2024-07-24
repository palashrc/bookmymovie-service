package com.bookmymovie.order.converter;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.order.model.OrderRequest;
import com.bookmymovie.order.model.payment.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderConverter {

    public PaymentRequest convertOrderToPayment(OrderRequest orderRequest) throws CoversionException {
        if (orderRequest == null) {
            log.error("Order to Payment Conversion Failed!");
            throw new CoversionException();
        }
        PaymentRequest paymentRequest = new PaymentRequest();
        return paymentRequest;
    }
}
