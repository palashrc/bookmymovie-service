package com.bookmymovie.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieShow {

    private Long movieShowId;

    private Long movieId;

    private Long cityId;

    private Long theaterId;

    private Long screenId;

    private List<String> showTimes;

    private Boolean operational;
}
