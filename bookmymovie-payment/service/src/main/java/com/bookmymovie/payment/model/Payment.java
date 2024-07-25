package com.bookmymovie.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long id;

    private String paymentCategory;

    private BigDecimal finalAmount;

    private UPI upi;

    private NetBanking netBanking;

    private DebitCard debitCard;

    private CreditCard creditCard;
}
