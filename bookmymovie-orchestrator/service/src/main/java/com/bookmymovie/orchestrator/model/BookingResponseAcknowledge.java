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
public class BookingResponseAcknowledge {

    private List<Booking> bookings = new ArrayList<>();

    private String acknowledgeCode;

    private List<Error> errors = new ArrayList<>();
}
