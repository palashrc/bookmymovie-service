package com.bookmymovie.cinema.converter;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.cinema.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MovieConverter {

    public com.bookmymovie.cinema.entity.Movie convertModelToEntity(Movie movieModel) throws CoversionException {
        com.bookmymovie.cinema.entity.Movie movieEntity = new com.bookmymovie.cinema.entity.Movie();
        if (movieModel == null) {
            log.error("Model to Entity Conversion Failed!");
            throw new CoversionException();
        }
        movieEntity.setMovieId(movieModel.getMovieId());
        movieEntity.setMovieName(movieModel.getMovieName());
        movieEntity.setMovieCategory(movieModel.getMovieCategory());
        movieEntity.setMovieDescription(movieModel.getMovieDescription());
        movieEntity.setMovieTrailerPath(movieModel.getMovieTrailerPath());
        movieEntity.setMovieCertificate(movieModel.getMovieCertificate());
        movieEntity.setMovieDuration(movieModel.getMovieDuration());
        movieEntity.setMovieCasts(movieModel.getMovieCasts());
        movieEntity.setMovieCrews(movieModel.getMovieCrews());
        movieEntity.setOperational(movieModel.getOperational());
        return movieEntity;
    }

    public Movie convertEntityToModel(com.bookmymovie.cinema.entity.Movie movieEntity) throws CoversionException {
        Movie movieModel = new Movie();
        if (movieEntity == null) {
            log.error("Entity to Model Conversion Failed!");
            throw new CoversionException();
        }
        movieModel.setMovieId(movieEntity.getMovieId());
        movieModel.setMovieName(movieEntity.getMovieName());
        movieModel.setMovieCategory(movieEntity.getMovieCategory());
        movieModel.setMovieDescription(movieEntity.getMovieDescription());
        movieModel.setMovieTrailerPath(movieEntity.getMovieTrailerPath());
        movieModel.setMovieCertificate(movieEntity.getMovieCertificate());
        movieModel.setMovieDuration(movieEntity.getMovieDuration());
        movieModel.setMovieCasts(movieEntity.getMovieCasts());
        movieModel.setMovieCrews(movieEntity.getMovieCrews());
        movieModel.setOperational(movieEntity.getOperational());
        return movieModel;
    }
}
