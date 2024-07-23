package com.bookmymovie.theater.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Seat {

    private Long seatId;

    private Long screenId;

    private String seatNumber;

    private String seatType;

    private String seatPrice;

    private Boolean operational;

    public Seat(com.bookmymovie.theater.entity.Seat seat){
        this.seatId = seat.getSeatId();
        this.screenId = seat.getScreenId();
        this.seatNumber = seat.getSeatNumber();
        this.seatType = seat.getSeatType();
        this.seatPrice = seat.getSeatPrice();
        this.operational = seat.getOperational();
    }
}
