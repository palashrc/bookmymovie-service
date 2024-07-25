package com.bookmymovie.orchestrator.model;

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
public class OrderResponseAsync {

    private String transactionId;

    private String orderId;

    private Boolean paymentConfirmation;

    private String paymentCategory;

    private BigDecimal finalAmount;

    private LocalDateTime orderTimeStamp;

    private String successCode;

    private String successMessage;

    private List<Error> errors = new ArrayList<>();
}
