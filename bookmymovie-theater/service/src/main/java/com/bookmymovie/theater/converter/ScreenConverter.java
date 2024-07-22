package com.bookmymovie.theater.converter;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.theater.model.Screen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScreenConverter {

    public com.bookmymovie.theater.entity.Screen convertModelToEntity(Screen screenModel) throws CoversionException {
        com.bookmymovie.theater.entity.Screen screenEntity = new com.bookmymovie.theater.entity.Screen();
        if (screenModel == null) {
            log.error("Model to Entity Conversion Failed!");
            throw new CoversionException();
        }
        screenEntity.setScreenId(screenModel.getScreenId());
        screenEntity.setTheaterId(screenModel.getTheaterId());
        screenEntity.setScreenName(screenModel.getScreenName());
        screenEntity.setTotalRows(screenModel.getTotalRows());
        screenEntity.setNumberOfSeatsInEachRow(screenModel.getNumberOfSeatsInEachRow());
        screenEntity.setRowNames(screenModel.getRowNames());
        screenEntity.setRowNameTypeMap(screenModel.getRowNameTypeMap());
        screenEntity.setRowTypePriceMap(screenModel.getRowTypePriceMap());
        screenEntity.setOperational(screenModel.getOperational());
        return screenEntity;
    }

    public Screen convertEntityToModel(com.bookmymovie.theater.entity.Screen screenEntity) throws CoversionException {
        Screen screenModel = new Screen();
        if (screenEntity == null) {
            log.error("Entity to Model Conversion Failed!");
            throw new CoversionException();
        }
        screenModel.setScreenId(screenEntity.getScreenId());
        screenModel.setTheaterId(screenEntity.getTheaterId());
        screenModel.setScreenName(screenEntity.getScreenName());
        screenModel.setTotalRows(screenEntity.getTotalRows());
        screenModel.setNumberOfSeatsInEachRow(screenEntity.getNumberOfSeatsInEachRow());
        screenModel.setRowNames(screenEntity.getRowNames());
        screenModel.setRowNameTypeMap(screenEntity.getRowNameTypeMap());
        screenModel.setRowTypePriceMap(screenEntity.getRowTypePriceMap());
        screenModel.setOperational(screenEntity.getOperational());
        return screenModel;
    }
}
