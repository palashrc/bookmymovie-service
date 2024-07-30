package com.bookmymovie.cinema.service;

import com.bookmymovie.cinema.converter.MovieConverter;
import com.bookmymovie.cinema.constant.ExceptionConstants;
import com.bookmymovie.cinema.helper.StatusMapper;
import com.bookmymovie.cinema.model.*;
import com.bookmymovie.cinema.repository.MovieRepository;
import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.RecordNotFoundException;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
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

    public MovieResponse getMovieById(MovieRequest movieRequest) {
        MovieResponse movieResponse = new MovieResponse();
        try {
            com.bookmymovie.cinema.entity.Movie movieRes = movieRepository.findById(movieRequest.getMovie().getMovieId()).get();
            Movie movie = movieConverter.convertEntityToModel(movieRes);
            movieResponse.getMovie().add(movie);
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

    public MovieResponse updateMovieOperational(MovieRequest movieRequest) {
        MovieResponse movieResponse = new MovieResponse();
        try {
            com.bookmymovie.cinema.entity.Movie movieRes = movieRepository.findById(movieRequest.getMovie().getMovieId()).get();
            if(ObjectUtils.isEmpty(movieRes)) {
                throw new RecordNotFoundException();
            }
            movieRes.setOperational(movieRequest.getMovie().getOperational());
            com.bookmymovie.cinema.entity.Movie movieUpdated = movieRepository.save(movieRes);
            movieResponse.getMovie().add(movieConverter.convertEntityToModel(movieUpdated));
            statusMapper.mapSuccessCodeMsg(movieResponse);
        } catch(RecordNotFoundException ex) {
            log.error("RecordNotFoundException Occurs!");
            ex.printStackTrace();
            movieResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE));
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

    public MovieResponse getMovieByIdForUser(MovieRequest movieRequest) {
        MovieResponse movieResponse = new MovieResponse();
        try {
            com.bookmymovie.cinema.entity.Movie movieRes = movieRepository.findById(movieRequest.getMovie().getMovieId()).get();
            Movie movie = movieConverter.convertEntityToModel(movieRes);
            if(BooleanUtils.isTrue(movie.getOperational())) {
                movieResponse.getMovie().add(movie);
            }
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
