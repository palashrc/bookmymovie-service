package com.bookmymovie.cinema.service;

import com.bookmymovie.cinema.converter.MovieConverter;
import com.bookmymovie.cinema.constant.ExceptionConstants;
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
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            movieResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            movieResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            movieResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return movieResponse;
    }
}
