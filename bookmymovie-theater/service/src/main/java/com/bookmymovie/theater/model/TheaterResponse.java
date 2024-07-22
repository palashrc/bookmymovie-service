package com.bookmymovie.theater.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import com.bookmymovie.core.error.Error;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TheaterResponse {

    private List<Theater> theaters = new ArrayList<>();

    private String successCode;

    private String successMessage;

    private List<Error> errors = new ArrayList<>();
}
