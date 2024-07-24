package com.bookmymovie.cinema.service;

import com.bookmymovie.cinema.converter.MovieShowConverter;
import com.bookmymovie.cinema.constant.ExceptionConstants;
import com.bookmymovie.cinema.helper.StatusMapper;
import com.bookmymovie.cinema.model.MovieShowRequest;
import com.bookmymovie.cinema.model.MovieShowResponse;
import com.bookmymovie.cinema.repository.MovieShowRepository;
import com.bookmymovie.core.error.CoversionException;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieShowService {

    @Autowired
    private MovieShowConverter movieShowConverter;

    @Autowired
    private MovieShowRepository movieShowRepository;

    @Autowired
    private StatusMapper statusMapper;

    public MovieShowResponse createMovieShow(MovieShowRequest movieShowRequest) {
        MovieShowResponse movieShowResponse = new MovieShowResponse();
        try {
            com.bookmymovie.cinema.entity.MovieShow movieShowEntity = movieShowConverter.convertModelToEntity(movieShowRequest.getMovieshow());
            com.bookmymovie.cinema.entity.MovieShow movieShowEntityRes = movieShowRepository.save(movieShowEntity);
            movieShowResponse.getMovieshow().add(movieShowConverter.convertEntityToModel(movieShowEntityRes));
            statusMapper.mapSuccessCodeMsg(movieShowResponse);
        } catch(CoversionException ex) {
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            movieShowResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            movieShowResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            movieShowResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return movieShowResponse;
    }
}
