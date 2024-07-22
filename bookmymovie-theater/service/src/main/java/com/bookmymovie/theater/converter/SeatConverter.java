package com.bookmymovie.theater.converter;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.theater.model.Seat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SeatConverter {

    public com.bookmymovie.theater.entity.Seat convertModelToEntity(Seat seatModel) throws CoversionException {
        com.bookmymovie.theater.entity.Seat seatEntity = new com.bookmymovie.theater.entity.Seat();
        if (seatModel == null) {
            log.error("Model to Entity Conversion Failed!");
            throw new CoversionException();
        }
        seatEntity.setSeatId(seatModel.getSeatId());
        seatEntity.setScreenId(seatModel.getScreenId());
        seatEntity.setSeatType(seatModel.getSeatType());
        seatEntity.setSeatRow(seatModel.getSeatRow());
        seatEntity.setSeatNumber(seatModel.getSeatNumber());
        seatEntity.setOperational(seatModel.getOperational());
        return seatEntity;
    }

    public Seat convertEntityToModel(com.bookmymovie.theater.entity.Seat seatEntity) throws CoversionException {
        Seat seatModel = new Seat();
        if (seatEntity == null) {
            log.error("Entity to Model Conversion Failed!");
            throw new CoversionException();
        }
        seatModel.setSeatId(seatEntity.getSeatId());
        seatModel.setScreenId(seatEntity.getScreenId());
        seatModel.setSeatType(seatEntity.getSeatType());
        seatModel.setSeatRow(seatEntity.getSeatRow());
        seatModel.setSeatNumber(seatEntity.getSeatNumber());
        seatModel.setOperational(seatEntity.getOperational());
        return seatModel;
    }
}
