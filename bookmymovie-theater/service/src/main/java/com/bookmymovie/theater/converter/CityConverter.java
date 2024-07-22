package com.bookmymovie.theater.converter;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.theater.model.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CityConverter {

    public com.bookmymovie.theater.entity.City convertModelToEntity(City cityModel) throws CoversionException {
        com.bookmymovie.theater.entity.City cityEntity = new com.bookmymovie.theater.entity.City();
        if (cityModel == null) {
            log.error("Model to Entity Conversion Failed!");
            throw new CoversionException();
        }
        cityEntity.setCityId(cityModel.getCityId());
        cityEntity.setCityName(cityModel.getCityName());
        cityEntity.setCityCode(cityModel.getCityCode());
        cityEntity.setOperational(cityModel.getOperational());
        return cityEntity;
    }

    public City convertEntityToModel(com.bookmymovie.theater.entity.City cityEntity) throws CoversionException {
        City cityModel = new City();
        if (cityEntity == null) {
            log.error("Entity to Model Conversion Failed!");
            throw new CoversionException();
        }
        cityModel.setCityId(cityEntity.getCityId());
        cityModel.setCityName(cityEntity.getCityName());
        cityModel.setCityCode(cityEntity.getCityCode());
        cityModel.setOperational(cityEntity.getOperational());
        return cityModel;
    }
}
