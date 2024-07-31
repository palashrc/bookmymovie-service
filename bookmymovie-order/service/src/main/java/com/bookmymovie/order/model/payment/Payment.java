package com.bookmymovie.order.model.payment;

import com.bookmymovie.order.model.CreditCard;
import com.bookmymovie.order.model.DebitCard;
import com.bookmymovie.order.model.NetBanking;
import com.bookmymovie.order.model.UPI;
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

    private String finalAmount;

    private UPI upi;

    private NetBanking netBanking;

    private DebitCard debitCard;

    private CreditCard creditCard;
}
