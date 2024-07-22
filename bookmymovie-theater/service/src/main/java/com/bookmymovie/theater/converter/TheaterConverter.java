package com.bookmymovie.theater.converter;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.theater.model.Theater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TheaterConverter {

    public com.bookmymovie.theater.entity.Theater convertModelToEntity(Theater theaterModel) throws CoversionException {
        com.bookmymovie.theater.entity.Theater theaterEntity = new com.bookmymovie.theater.entity.Theater();
        if (theaterModel == null) {
            log.error("Model to Entity Conversion Failed!");
            throw new CoversionException();
        }
        theaterEntity.setTheaterId(theaterModel.getTheaterId());
        theaterEntity.setCityId(theaterModel.getCityId());
        theaterEntity.setTheaterName(theaterModel.getTheaterName());
        theaterEntity.setAddressLine(theaterModel.getAddressLine());
        theaterEntity.setAvailableFacilities(theaterModel.getAvailableFacilities());
        theaterEntity.setOperational(theaterModel.getOperational());
        return theaterEntity;
    }

    public Theater convertEntityToModel(com.bookmymovie.theater.entity.Theater theaterEntity) throws CoversionException {
        Theater theaterModel = new Theater();
        if (theaterEntity == null) {
            log.error("Entity to Model Conversion Failed!");
            throw new CoversionException();
        }
        theaterModel.setTheaterId(theaterEntity.getTheaterId());
        theaterModel.setCityId(theaterEntity.getCityId());
        theaterModel.setTheaterName(theaterEntity.getTheaterName());
        theaterModel.setAddressLine(theaterEntity.getAddressLine());
        theaterModel.setAvailableFacilities(theaterEntity.getAvailableFacilities());
        theaterModel.setOperational(theaterEntity.getOperational());
        return theaterModel;
    }
}
