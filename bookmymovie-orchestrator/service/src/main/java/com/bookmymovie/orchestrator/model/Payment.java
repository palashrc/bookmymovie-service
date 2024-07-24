package com.bookmymovie.orchestrator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long id;

    private String paymentCategory;

    private UPI upi;

    private NetBanking netBanking;

    private DebitCard debitCard;

    private CreditCard creditCard;
}
