package com.bookmymovie.orchestrator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import com.google.cloud.spring.data.datastore.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "OrchRequestTracker")
public class OrchRequestTracker {

    @Id
    @JsonProperty("id")
    @Field(name = "Id")
    private Long id;

    @JsonProperty("transactionId")
    @Field(name = "TransactionId")
    private String transactionId;

    @JsonProperty("reqTimeStamp")
    @Field(name = "ReqTimeStamp")
    private LocalDateTime reqTimeStamp;

    @JsonProperty("resInterimTimeStamp")
    @Field(name = "ResInterimTimeStamp")
    private LocalDateTime resInterimTimeStamp;

    @JsonProperty("resFinalTimeStamp")
    @Field(name = "ResFinalTimeStamp")
    private LocalDateTime resFinalTimeStamp;

    @JsonProperty("txnStatus")
    @Field(name = "TxnStatus")
    private String txnStatus;
}
