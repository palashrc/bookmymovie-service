package com.bookmymovie.order.util;

import com.bookmymovie.order.constant.CommonConstants;
import com.bookmymovie.order.model.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class OrderUtils {

    public static BigDecimal calculateTotalAmount(OrderRequest orderRequest) {
        BigDecimal totalAmount = orderRequest.getOrder()
                .getSeatBook()
                .stream()
                .map(sb -> new BigDecimal(sb.getSeatPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("Total Amount: " + totalAmount);
        return totalAmount;
    }

    public static BigDecimal calculateConvenienceFees(BigDecimal totalAmountAfterDiscounts, BigDecimal percentage) {
        BigDecimal convenienceFees = totalAmountAfterDiscounts.multiply(percentage).divide(CommonConstants.ONE_HUNDRED, RoundingMode.HALF_UP);
        log.info("Convenience Fees Amount: " + convenienceFees);
        return totalAmountAfterDiscounts.add(convenienceFees);
    }
}
