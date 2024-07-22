package com.bookmymovie.theater.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.theater.converter.SeatConverter;
import com.bookmymovie.theater.helper.Constants;
import com.bookmymovie.theater.helper.StatusMapper;
import com.bookmymovie.theater.model.SeatRequest;
import com.bookmymovie.theater.model.SeatResponse;
import com.bookmymovie.theater.repository.SeatRepository;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SeatService {

    @Autowired
    private SeatConverter seatConverter;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private StatusMapper statusMapper;

    public SeatResponse createSeats(SeatRequest seatRequest) {
        SeatResponse seatResponse = new SeatResponse();
        try {
            com.bookmymovie.theater.entity.Seat seatEntity = seatConverter.convertModelToEntity(seatRequest.getSeat());
            com.bookmymovie.theater.entity.Seat seatEntityRes = seatRepository.save(seatEntity);
            seatResponse.getSeats().add(seatConverter.convertEntityToModel(seatEntityRes));
            statusMapper.mapSuccessCodeMsg(seatResponse);
        } catch(CoversionException ex) {
            seatResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            seatResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            seatResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.EXCEPTION_TYPE));
        }
        return seatResponse;
    }
}
