package com.bookmymovie.theater.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.theater.converter.TheaterConverter;
import com.bookmymovie.theater.model.Theater;
import com.bookmymovie.theater.helper.Constants;
import com.bookmymovie.theater.helper.StatusMapper;
import com.bookmymovie.theater.model.TheaterRequest;
import com.bookmymovie.theater.model.TheaterResponse;
import com.bookmymovie.theater.repository.TheaterRepository;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class TheaterService {

    @Autowired
    private TheaterConverter theaterConverter;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private StatusMapper statusMapper;

    public TheaterResponse saveTheater(TheaterRequest theaterRequest) {
        TheaterResponse theaterResponse = new TheaterResponse();
        try {
            com.bookmymovie.theater.entity.Theater theaterEntity = theaterConverter.convertModelToEntity(theaterRequest.getTheater());
            com.bookmymovie.theater.entity.Theater theaterEntityRes = theaterRepository.save(theaterEntity);
            theaterResponse.getTheaters().add(theaterConverter.convertEntityToModel(theaterEntityRes));
            statusMapper.mapSuccessCodeMsg(theaterResponse);
        } catch(CoversionException ex) {
            theaterResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            theaterResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            theaterResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.EXCEPTION_TYPE));
        }
        return theaterResponse;
    }

    public TheaterResponse getTheater() {
        TheaterResponse theaterResponse = new TheaterResponse();
        try {
            Iterable<com.bookmymovie.theater.entity.Theater> theaterIterable = theaterRepository.findAll();
            List<com.bookmymovie.theater.entity.Theater> theaterList = Streamable.of(theaterIterable).toList();
            List<Theater> theaterListRes = theaterList.stream().map(com.bookmymovie.theater.model.Theater::new).toList();
            theaterResponse.getTheaters().addAll(theaterListRes);
            statusMapper.mapSuccessCodeMsg(theaterResponse);
        } catch(DatastoreException ex) {
            theaterResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            theaterResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.EXCEPTION_TYPE));
        }
        return theaterResponse;
    }
}
