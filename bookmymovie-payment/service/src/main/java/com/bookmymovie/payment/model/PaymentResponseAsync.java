package com.bookmymovie.payment.model;

import com.bookmymovie.core.error.Error;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseAsync {

    private String transactionId;

    private Long paymentId;

    private String paymentCategory;

    private String finalAmount;

    private LocalDateTime paymentTimeStamp;

    private String successCode;

    private String successMessage;

    private List<Error> errors = new ArrayList<>();

}
