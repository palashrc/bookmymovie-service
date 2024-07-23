package com.bookmymovie.cinema.model;

import com.bookmymovie.core.error.Error;
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
public class MovieShowResponse {

    private List<MovieShow> movieshow = new ArrayList<>();

    private String successCode;

    private String successMessage;

    private List<Error> errors = new ArrayList<>();
}
