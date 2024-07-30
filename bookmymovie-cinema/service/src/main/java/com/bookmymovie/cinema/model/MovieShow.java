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

    private String theaterName;

    private Long screenId;

    private String screenName;

    private List<String> showTimes;

    private Boolean operational;

    public MovieShow(com.bookmymovie.cinema.entity.MovieShow movieShow){
        this.movieShowId = movieShow.getMovieShowId();
        this.movieId = movieShow.getMovieId();
        this.cityId = movieShow.getCityId();
        this.theaterId = movieShow.getTheaterId();
        this.theaterName = movieShow.getTheaterName();
        this.screenId = movieShow.getScreenId();
        this.screenName = movieShow.getScreenName();
        this.showTimes = movieShow.getShowTimes();
        this.operational = movieShow.getOperational();
    }
}
