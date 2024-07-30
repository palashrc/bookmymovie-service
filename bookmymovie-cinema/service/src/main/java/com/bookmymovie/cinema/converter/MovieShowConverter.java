package com.bookmymovie.cinema.converter;

import com.bookmymovie.cinema.model.MovieShow;
import com.bookmymovie.core.error.CoversionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MovieShowConverter {

    public com.bookmymovie.cinema.entity.MovieShow convertModelToEntity(MovieShow movieShowModel) throws CoversionException {
        com.bookmymovie.cinema.entity.MovieShow movieShowEntity = new com.bookmymovie.cinema.entity.MovieShow();
        if (movieShowModel == null) {
            log.error("Model to Entity Conversion Failed!");
            throw new CoversionException();
        }
        movieShowEntity.setMovieShowId(movieShowModel.getMovieShowId());
        movieShowEntity.setMovieId(movieShowModel.getMovieId());
        movieShowEntity.setCityId(movieShowModel.getCityId());
        movieShowEntity.setTheaterId(movieShowModel.getTheaterId());
        movieShowEntity.setTheaterName(movieShowModel.getTheaterName());
        movieShowEntity.setScreenId(movieShowModel.getScreenId());
        movieShowEntity.setScreenName(movieShowModel.getScreenName());
        movieShowEntity.setShowTimes(movieShowModel.getShowTimes());
        movieShowEntity.setOperational(movieShowModel.getOperational());
        return movieShowEntity;
    }

    public MovieShow convertEntityToModel(com.bookmymovie.cinema.entity.MovieShow movieShowEntity) throws CoversionException {
        MovieShow movieShowModel = new MovieShow();
        if (movieShowEntity == null) {
            log.error("Entity to Model Conversion Failed!");
            throw new CoversionException();
        }
        movieShowModel.setMovieShowId(movieShowEntity.getMovieShowId());
        movieShowModel.setMovieId(movieShowEntity.getMovieId());
        movieShowModel.setCityId(movieShowEntity.getCityId());
        movieShowModel.setTheaterId(movieShowEntity.getTheaterId());
        movieShowModel.setTheaterName(movieShowEntity.getTheaterName());
        movieShowModel.setScreenId(movieShowEntity.getScreenId());
        movieShowModel.setScreenName(movieShowEntity.getScreenName());
        movieShowModel.setShowTimes(movieShowEntity.getShowTimes());
        movieShowModel.setOperational(movieShowEntity.getOperational());
        return movieShowModel;
    }
}
