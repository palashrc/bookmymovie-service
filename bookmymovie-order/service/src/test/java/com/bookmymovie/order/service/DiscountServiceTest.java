package com.bookmymovie.order.service;

import com.bookmymovie.order.model.Order;
import com.bookmymovie.order.model.OrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountServiceTest {

    private DiscountService discountServiceUnderTest;

    @BeforeEach
    void setUp() {
        discountServiceUnderTest = new DiscountService();
        ReflectionTestUtils.setField(discountServiceUnderTest, "afternoonDiscountStartHour",
                "afternoonDiscountStartHour");
        ReflectionTestUtils.setField(discountServiceUnderTest, "afternoonDiscountEndHour", "afternoonDiscountEndHour");
        ReflectionTestUtils.setField(discountServiceUnderTest, "afternoonDiscountPercentage",
                "afternoonDiscountPercentage");
    }

    @Test
    void testApplyDiscounts() {
        // Setup
        final OrderRequest orderRequest = OrderRequest.builder()
                .order(Order.builder()
                        .showdate("showdate")
                        .build())
                .build();

        // Run the test
        final BigDecimal result = discountServiceUnderTest.applyDiscounts(orderRequest, new BigDecimal("0.00"));

        // Verify the results
        assertThat(result).isEqualTo(new BigDecimal("0.00"));
    }
}
