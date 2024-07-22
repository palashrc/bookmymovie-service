package com.bookmymovie.viewer.converter;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.viewer.model.Viewer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ViewerConverter {

    public com.bookmymovie.viewer.entity.Viewer convertModelToEntity(Viewer viewerModel) throws CoversionException {
        com.bookmymovie.viewer.entity.Viewer viewerEntity = new com.bookmymovie.viewer.entity.Viewer();
        if (viewerModel == null) {
            log.error("Model to Entity Conversion Failed!");
            throw new CoversionException();
        }
        viewerEntity.setViewerId(viewerModel.getViewerId());
        viewerEntity.setFirstName(viewerModel.getFirstName());
        viewerEntity.setLastName(viewerModel.getLastName());
        viewerEntity.setMobile(viewerModel.getMobile());
        viewerEntity.setEmail(viewerModel.getEmail());
        viewerEntity.setDob(viewerModel.getDob());
        viewerEntity.setAnniversary(viewerModel.getAnniversary());
        viewerEntity.setGender(viewerModel.getGender());
        viewerEntity.setState(viewerModel.getState());
        viewerEntity.setCity(viewerModel.getCity());
        viewerEntity.setAddressLine(viewerModel.getAddressLine());
        viewerEntity.setPinCode(viewerModel.getPinCode());
        return viewerEntity;
    }

    public Viewer convertEntityToModel(com.bookmymovie.viewer.entity.Viewer viewerEntity) throws CoversionException {
        Viewer viewerModel = new Viewer();
        if (viewerEntity == null) {
            log.error("Entity to Model Conversion Failed!");
            throw new CoversionException();
        }
        viewerModel.setViewerId(viewerEntity.getViewerId());
        viewerModel.setFirstName(viewerEntity.getFirstName());
        viewerModel.setLastName(viewerEntity.getLastName());
        viewerModel.setMobile(viewerEntity.getMobile());
        viewerModel.setEmail(viewerEntity.getEmail());
        viewerModel.setDob(viewerEntity.getDob());
        viewerModel.setAnniversary(viewerEntity.getAnniversary());
        viewerModel.setGender(viewerEntity.getGender());
        viewerModel.setState(viewerEntity.getState());
        viewerModel.setCity(viewerEntity.getCity());
        viewerModel.setAddressLine(viewerEntity.getAddressLine());
        viewerModel.setPinCode(viewerEntity.getPinCode());
        return viewerModel;
    }
}
