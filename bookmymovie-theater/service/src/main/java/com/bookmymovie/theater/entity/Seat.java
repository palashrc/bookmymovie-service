package com.bookmymovie.theater.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import com.google.cloud.spring.data.datastore.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Seat")
public class Seat {

    @Id
    @JsonProperty("seatId")
    @Field(name = "SeatId")
    private Long seatId;

    @JsonProperty("screenId")
    @Field(name = "ScreenId")
    private Long screenId;

    @JsonProperty("seatType")
    @Field(name = "SeatType")
    private String seatType;

    @JsonProperty("seatRow")
    @Field(name = "SeatRow")
    private String seatRow;

    @JsonProperty("seatNumber")
    @Field(name = "SeatNumber")
    private String seatNumber;

    @JsonProperty("operational")
    @Field(name = "Operational")
    private Boolean operational;
}
