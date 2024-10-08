package com.bookmymovie.orchestrator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private List<Order> orders = new ArrayList<>();

    private String transactionId;

    private String successCode;

    private String successMessage;

    private List<Error> errors = new ArrayList<>();
}
