package com.bookmymovie.order.model;

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

    private Long transactionId;

    private String successCode;

    private String successMessage;

    private List<Error> errors = new ArrayList<>();
}
