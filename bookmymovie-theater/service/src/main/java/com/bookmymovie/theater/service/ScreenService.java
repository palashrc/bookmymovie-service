package com.bookmymovie.theater.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.RecordNotFoundException;
import com.bookmymovie.theater.converter.ScreenConverter;
import com.bookmymovie.theater.constant.ExceptionConstants;
import com.bookmymovie.theater.helper.StatusMapper;
import com.bookmymovie.theater.model.*;
import com.bookmymovie.theater.repository.ScreenRepository;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScreenService {

    @Autowired
    private ScreenConverter screenConverter;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private StatusMapper statusMapper;

    public ScreenResponse saveScreen(ScreenRequest screenRequest) {
        ScreenResponse screenResponse = new ScreenResponse();
        try {
            com.bookmymovie.theater.entity.Screen screenEntity = screenConverter.convertModelToEntity(screenRequest.getScreen());
            com.bookmymovie.theater.entity.Screen screenEntityRes = screenRepository.save(screenEntity);
            screenResponse.getScreens().add(screenConverter.convertEntityToModel(screenEntityRes));
            statusMapper.mapSuccessCodeMsg(screenResponse);
        } catch(CoversionException ex) {
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            screenResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            screenResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            screenResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return screenResponse;
    }

    public ScreenResponse updateScreenOperational(ScreenRequest screenRequest) {
        ScreenResponse screenResponse = new ScreenResponse();
        try {
            com.bookmymovie.theater.entity.Screen screenEntityRes = screenRepository.findById(screenRequest.getScreen().getScreenId()).get();
            if(ObjectUtils.isEmpty(screenEntityRes)) {
                throw new RecordNotFoundException();
            }
            screenEntityRes.setOperational(screenRequest.getScreen().getOperational());
            com.bookmymovie.theater.entity.Screen screenResUpdated = screenRepository.save(screenEntityRes);
            screenResponse.getScreens().add(screenConverter.convertEntityToModel(screenResUpdated));
            statusMapper.mapSuccessCodeMsg(screenResponse);
        } catch(RecordNotFoundException ex) {
            log.error("RecordNotFoundException Occurs!");
            ex.printStackTrace();
            screenResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE));
        } catch(CoversionException ex) {
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            screenResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            screenResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            screenResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return screenResponse;
    }
}
