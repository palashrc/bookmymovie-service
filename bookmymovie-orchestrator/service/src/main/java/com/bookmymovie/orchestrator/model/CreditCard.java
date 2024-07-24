package com.bookmymovie.orchestrator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    private String bankName;

    private String cardNumber;

    private String cvv;

    private String expiry;

    private String nameOnCard;
}
