package com.bookmymovie.orchestrator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private Long bookingId;

    private Long viewerId;

    private String firstName;

    private String lastName;

    private String mobile;

    private String email;

    private Long movieId;

    private String movieName;

    private String movieCertificate;

    private Long theaterId;

    private String theaterName;

    private Long screenId;

    private String screenName;

    private String showdate;

    private List<SeatBook> seatBook;
}
