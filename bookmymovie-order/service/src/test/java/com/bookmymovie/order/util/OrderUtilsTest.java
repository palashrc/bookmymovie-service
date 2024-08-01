package com.bookmymovie.order.util;

import com.bookmymovie.order.model.Order;
import com.bookmymovie.order.model.OrderRequest;
import com.bookmymovie.order.model.SeatBook;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderUtilsTest {

    @Test
    void testCalculateTotalAmount() {
        // Setup
        final OrderRequest orderRequest = OrderRequest.builder()
                .order(Order.builder()
                        .seatBook(List.of(SeatBook.builder()
                                .seatPrice("seatPrice")
                                .build()))
                        .build())
                .build();

        // Run the test
        final BigDecimal result = OrderUtils.calculateTotalAmount(orderRequest);

        // Verify the results
        assertThat(result).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void testCalculateConvenienceFees() {
        assertThat(OrderUtils.calculateConvenienceFees(new BigDecimal("0.00"), new BigDecimal("0.00")))
                .isEqualTo(new BigDecimal("0.00"));
    }
}
