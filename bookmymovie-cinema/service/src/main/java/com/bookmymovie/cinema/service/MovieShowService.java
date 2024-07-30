package com.bookmymovie.cinema.service;

import com.bookmymovie.cinema.converter.MovieShowConverter;
import com.bookmymovie.cinema.constant.ExceptionConstants;
import com.bookmymovie.cinema.helper.StatusMapper;
import com.bookmymovie.cinema.model.MovieShow;
import com.bookmymovie.cinema.model.MovieShowRequest;
import com.bookmymovie.cinema.model.MovieShowResponse;
import com.bookmymovie.cinema.repository.MovieShowRepository;
import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.RecordNotFoundException;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public MovieShowResponse updateMovieShowOperational(MovieShowRequest movieShowRequest) {
        MovieShowResponse movieShowResponse = new MovieShowResponse();
        try {
            com.bookmymovie.cinema.entity.MovieShow movieShowRes = movieShowRepository.findById(movieShowRequest.getMovieshow().getMovieShowId()).get();
            if(ObjectUtils.isEmpty(movieShowRes)) {
                throw new RecordNotFoundException();
            }
            movieShowRes.setOperational(movieShowRequest.getMovieshow().getOperational());
            com.bookmymovie.cinema.entity.MovieShow movieShowUpdated = movieShowRepository.save(movieShowRes);
            movieShowResponse.getMovieshow().add(movieShowConverter.convertEntityToModel(movieShowUpdated));
            statusMapper.mapSuccessCodeMsg(movieShowResponse);
        } catch(RecordNotFoundException ex) {
            log.error("RecordNotFoundException Occurs!");
            ex.printStackTrace();
            movieShowResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE));
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

    public MovieShowResponse getMovieShowByCityAndMovie(MovieShowRequest movieShowRequest) {
        MovieShowResponse movieShowResponse = new MovieShowResponse();
        try {
            Optional<List<com.bookmymovie.cinema.entity.MovieShow>> movieShowOptional = movieShowRepository.findByMovieIdAndCityId(movieShowRequest.getMovieshow().getMovieId(), movieShowRequest.getMovieshow().getCityId());
            if (movieShowOptional.isPresent()) {
                List<com.bookmymovie.cinema.entity.MovieShow> movieShowEntityRes = movieShowOptional.get();
                if(!CollectionUtils.isEmpty(movieShowEntityRes)) {
                    List<MovieShow> movieShowModelRes = movieShowEntityRes.stream().map(MovieShow::new).collect(Collectors.toList());
                    List<MovieShow> movieShowFilteredModelRes = movieShowModelRes.stream()
                                                                                 .filter(c -> Objects.nonNull(c))
                                                                                 .filter(c -> Objects.nonNull(c.getOperational()))
                                                                                 .filter(c -> BooleanUtils.isTrue(c.getOperational()))
                                                                                 .collect(Collectors.toList());
                    if(!CollectionUtils.isEmpty(movieShowFilteredModelRes)) {
                        movieShowResponse.getMovieshow().addAll(movieShowFilteredModelRes);
                    }
                }
            }
            statusMapper.mapSuccessCodeMsg(movieShowResponse);
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
