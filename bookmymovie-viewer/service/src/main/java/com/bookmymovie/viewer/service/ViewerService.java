package com.bookmymovie.viewer.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.RegistrationException;
import com.bookmymovie.viewer.converter.ViewerConverter;
import com.bookmymovie.viewer.constant.ExceptionConstants;
import com.bookmymovie.viewer.helper.StatusMapper;
import com.bookmymovie.viewer.model.Viewer;
import com.bookmymovie.viewer.model.ViewerRequest;
import com.bookmymovie.viewer.model.ViewerResponse;
import com.bookmymovie.viewer.repository.ViewerRepository;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class ViewerService {

    @Autowired
    private ViewerConverter viewerConverter;

    @Autowired
    private ViewerRepository viewerRepository;

    @Autowired
    private StatusMapper statusMapper;

    public ViewerResponse createViewer(ViewerRequest viewerRequest) {
        ViewerResponse viewerResponse = new ViewerResponse();
        try {
            if(!CollectionUtils.isEmpty(getViewerByMobile(viewerRequest).getViewers())) {
                log.error("User Exists. Use Different Mobile!");
                throw new RegistrationException();
            }
            com.bookmymovie.viewer.entity.Viewer viewerEntity = viewerConverter.convertModelToEntity(viewerRequest.getViewer());
            com.bookmymovie.viewer.entity.Viewer viewerEntityRes = viewerRepository.save(viewerEntity);
            viewerResponse.getViewers().add(viewerConverter.convertEntityToModel(viewerEntityRes));
            statusMapper.mapSuccessCodeMsg(viewerResponse);
        } catch(CoversionException ex) {
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            viewerResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        }  catch(RegistrationException ex) {
            log.error("RegistrationException Occurs!");
            ex.printStackTrace();
            viewerResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.REGISTRATION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            viewerResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            viewerResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return viewerResponse;
    }

    public ViewerResponse getViewerByMobile(ViewerRequest viewerRequest) {
        ViewerResponse viewerResponse = new ViewerResponse();
        try {
            com.bookmymovie.viewer.entity.Viewer viewerEntity = viewerRepository.findByMobile(viewerRequest.getViewer().getMobile()).get();
            Viewer viewer = viewerConverter.convertEntityToModel(viewerEntity);
            viewerResponse.getViewers().add(viewer);
            statusMapper.mapSuccessCodeMsg(viewerResponse);
        } catch(CoversionException ex) {
            viewerResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            viewerResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            viewerResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return viewerResponse;
    }
}
