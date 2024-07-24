package com.bookmymovie.order.model;

import com.bookmymovie.core.error.Error;
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
public class OrderResponseAck {

    private String transactionId;

    private String acknowledgeCode;

    private String successCode;

    private String successMessage;

    private List<Error> errors = new ArrayList<>();
}
