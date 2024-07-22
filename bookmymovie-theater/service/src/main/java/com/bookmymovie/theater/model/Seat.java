package com.bookmymovie.theater.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    private Long seatId;

    private Long screenId;

    private String seatType;

    private String seatRow;

    private String seatNumber;

    private Boolean operational;
}
