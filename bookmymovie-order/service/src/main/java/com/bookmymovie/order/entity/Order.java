package com.bookmymovie.order.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import com.google.cloud.spring.data.datastore.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Order")
public class Order {

    @Id
    @JsonProperty("id")
    @Field(name = "Id")
    private Long id;

    @JsonProperty("orderId")
    @Field(name = "OrderId")
    private String orderId;

    @JsonProperty("transactionId")
    @Field(name = "TransactionId")
    private String transactionId;

    @JsonProperty("paymentId")
    @Field(name = "PaymentId")
    private Long paymentId;

    @JsonProperty("paymentCategory")
    @Field(name = "PaymentCategory")
    private String paymentCategory;

    @JsonProperty("finalAmount")
    @Field(name = "FinalAmount")
    private String finalAmount;

    @JsonProperty("orderTimeStamp")
    @Field(name = "OrderTimeStamp")
    private LocalDateTime orderTimeStamp;
}
