package com.bookmymovie.cinema.service;

import com.bookmymovie.cinema.converter.MovieConverter;
import com.bookmymovie.cinema.helper.Constants;
import com.bookmymovie.cinema.helper.StatusMapper;
import com.bookmymovie.cinema.model.MovieRequest;
import com.bookmymovie.cinema.model.MovieResponse;
import com.bookmymovie.cinema.repository.MovieRepository;
import com.bookmymovie.core.error.CoversionException;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieService {

    @Autowired
    private MovieConverter movieConverter;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private StatusMapper statusMapper;

    public MovieResponse saveMovie(MovieRequest MovieRequest) {
        MovieResponse movieResponse = new MovieResponse();
        try {
            com.bookmymovie.cinema.entity.Movie movieEntity = movieConverter.convertModelToEntity(MovieRequest.getMovie());
            com.bookmymovie.cinema.entity.Movie movieEntityRes = movieRepository.save(movieEntity);
            movieResponse.getMovie().add(movieConverter.convertEntityToModel(movieEntityRes));
            statusMapper.mapSuccessCodeMsg(movieResponse);
        } catch(CoversionException ex) {
            ex.printStackTrace();
            movieResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            ex.printStackTrace();
            movieResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            ex.printStackTrace();
            movieResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.EXCEPTION_TYPE));
        }
        return movieResponse;
    }
}
