package com.bookmymovie.orchestrator.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long bookingId;

    private Long viewerId;

    private Long movieId;

    private Long screenId;

    private String showdate;

    private List<Long> seats;

}
