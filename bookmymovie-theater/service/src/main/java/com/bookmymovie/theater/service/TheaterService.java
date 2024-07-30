package com.bookmymovie.theater.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.RecordNotFoundException;
import com.bookmymovie.theater.converter.TheaterConverter;
import com.bookmymovie.theater.model.*;
import com.bookmymovie.theater.constant.ExceptionConstants;
import com.bookmymovie.theater.helper.StatusMapper;
import com.bookmymovie.theater.repository.TheaterRepository;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            theaterResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            theaterResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            theaterResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return theaterResponse;
    }

    public TheaterResponse updateTheaterOperational(TheaterRequest theaterRequest) {
        TheaterResponse theaterResponse = new TheaterResponse();
        try {
            com.bookmymovie.theater.entity.Theater theaterRes = theaterRepository.findById(theaterRequest.getTheater().getTheaterId()).get();
            if(ObjectUtils.isEmpty(theaterRes)) {
                throw new RecordNotFoundException();
            }
            theaterRes.setOperational(theaterRequest.getTheater().getOperational());
            com.bookmymovie.theater.entity.Theater theaterResUpdated = theaterRepository.save(theaterRes);
            theaterResponse.getTheaters().add(theaterConverter.convertEntityToModel(theaterResUpdated));
            statusMapper.mapSuccessCodeMsg(theaterResponse);
        } catch(RecordNotFoundException ex) {
            log.error("RecordNotFoundException Occurs!");
            ex.printStackTrace();
            theaterResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE));
        } catch(CoversionException ex) {
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            theaterResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            theaterResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            theaterResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return theaterResponse;
    }
}
