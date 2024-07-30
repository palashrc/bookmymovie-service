package com.bookmymovie.order.service;

import com.bookmymovie.core.util.CommonUtils;
import com.bookmymovie.order.constant.CommonConstants;
import com.bookmymovie.order.model.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@Slf4j
public class DiscountService {

    @Value("${discounts.rules.afternoonShow.startHour}")
    private String afternoonDiscountStartHour;

    @Value("${discounts.rules.afternoonShow.endHour}")
    private String afternoonDiscountEndHour;

    @Value("${discounts.rules.afternoonShow.percentage}")
    private String afternoonDiscountPercentage;

    public BigDecimal applyDiscounts(OrderRequest orderRequest, BigDecimal totalAmount) {
        totalAmount = applyAfternoonDiscount(orderRequest, totalAmount);
        log.info("Total Amount after all Discounts: " + totalAmount);
        return totalAmount;
    }

    private BigDecimal applyAfternoonDiscount(OrderRequest orderRequest, BigDecimal totalAmount) {
        String showDate = orderRequest.getOrder().getShowdate();
        LocalDateTime convertedDate = CommonUtils.getConvertedLocalDateTime(showDate);
        int startHour = Integer.parseInt(afternoonDiscountStartHour);
        int endHour = Integer.parseInt(afternoonDiscountEndHour);
        log.info("Afternoon StartHour: " + afternoonDiscountStartHour + " Afternoon EndHour: " + afternoonDiscountEndHour);
        if(isAfternoonDiscountApplicable(convertedDate, startHour, endHour)) {
            totalAmount = totalAmount.subtract(calculateAfternoonDiscount(totalAmount, new BigDecimal(afternoonDiscountPercentage)));
        }
        return totalAmount;
    }

    private Boolean isAfternoonDiscountApplicable(LocalDateTime convertedDate, int startHour, int endHour) {
        Boolean isApplicable = Boolean.FALSE;
        Boolean isAfternoonBegin = convertedDate.getHour() > startHour || convertedDate.getHour() == startHour;
        Boolean isAfternoonEnd = convertedDate.getHour() < endHour || convertedDate.getHour() == endHour;
        if(isAfternoonBegin && isAfternoonEnd) {
            log.info("You are eligible for Afternoon Discount!");
            isApplicable = Boolean.TRUE;
        }
        return isApplicable;
    }

    private static BigDecimal calculateAfternoonDiscount(BigDecimal totalAmount, BigDecimal percentage) {
        BigDecimal discountAmount = totalAmount.multiply(percentage).divide(CommonConstants.ONE_HUNDRED, RoundingMode.HALF_UP);
        log.info("Afternoon Discount Amount: " + discountAmount);
        return discountAmount;
    }
}
