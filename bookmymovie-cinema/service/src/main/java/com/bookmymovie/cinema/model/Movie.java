package com.bookmymovie.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private Long movieId;

    private String movieName;

    private String movieCategory;

    private String movieDescription;

    private String movieTrailerPath;

    private String movieCertificate;

    private String movieDuration;

    private List<String> movieCasts;

    private List<String> movieCrews;

    private Boolean operational;
}
