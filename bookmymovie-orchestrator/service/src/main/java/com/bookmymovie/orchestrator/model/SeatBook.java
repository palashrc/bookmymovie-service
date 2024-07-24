package com.bookmymovie.orchestrator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatBook {

    private Long seatId;

    private String seatNumber;

    private String seatType;

    private String seatPrice;

}
