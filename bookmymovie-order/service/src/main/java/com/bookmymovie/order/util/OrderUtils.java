package com.bookmymovie.order.util;

import com.bookmymovie.order.model.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class OrderUtils {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    public static BigDecimal calculateFinalAmount(OrderRequest orderRequest, BigDecimal percentage) {
        BigDecimal totalAmount = orderRequest.getOrder()
                .getSeatBook()
                .stream()
                .map(sb -> new BigDecimal(sb.getSeatPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("Total Amount: " + totalAmount);
        BigDecimal finalAmount = totalAmount.add(calculateConvenienceFees(totalAmount, percentage));
        log.info("Final Amount: " + finalAmount);
        return finalAmount;
    }

    private static BigDecimal calculateConvenienceFees(BigDecimal totalAmount, BigDecimal percentage) {
        BigDecimal convenienceFees = totalAmount.multiply(percentage).divide(ONE_HUNDRED, RoundingMode.HALF_UP);
        log.info("Convenience Fees Amount: " + convenienceFees);
        return convenienceFees;
    }
}
