package com.bookmymovie.theater.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.theater.converter.ScreenConverter;
import com.bookmymovie.theater.helper.Constants;
import com.bookmymovie.theater.helper.StatusMapper;
import com.bookmymovie.theater.model.*;
import com.bookmymovie.theater.repository.ScreenRepository;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
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
            screenResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            screenResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            screenResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.EXCEPTION_TYPE));
        }
        return screenResponse;
    }

    public ScreenResponse getScreenById(ScreenRequest screenRequest) {
        ScreenResponse screenResponse = new ScreenResponse();
        try {
            com.bookmymovie.theater.entity.Screen screenRes = screenRepository.findById(screenRequest.getScreen().getScreenId()).get();
            Screen screen = screenConverter.convertEntityToModel(screenRes);
            screenResponse.getScreens().add(screen);
            statusMapper.mapSuccessCodeMsg(screenResponse);
        } catch(CoversionException ex) {
            screenResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            screenResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            screenResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.EXCEPTION_TYPE));
        }
        return screenResponse;
    }
}
