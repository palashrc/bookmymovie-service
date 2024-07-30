package com.bookmymovie.theater.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.RecordNotFoundException;
import com.bookmymovie.theater.converter.ScreenConverter;
import com.bookmymovie.theater.converter.SeatConverter;
import com.bookmymovie.theater.constant.ExceptionConstants;
import com.bookmymovie.theater.helper.StatusMapper;
import com.bookmymovie.theater.model.*;
import com.bookmymovie.theater.repository.ScreenRepository;
import com.bookmymovie.theater.repository.SeatRepository;
import com.bookmymovie.theater.util.SeatUtils;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SeatService {

    @Autowired
    private ScreenConverter screenConverter;

    @Autowired
    private SeatConverter seatConverter;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private StatusMapper statusMapper;

    public SeatResponse createSeats(SeatRequest seatRequest) {
        SeatResponse seatResponse = new SeatResponse();
        List<com.bookmymovie.theater.entity.Seat> seatEntityList = new ArrayList<>();
        try {
            com.bookmymovie.theater.entity.Screen screenRes = screenRepository.findById(seatRequest.getSeat().getScreenId()).get();
            Screen screen = screenConverter.convertEntityToModel(screenRes);
            Iterable<com.bookmymovie.theater.entity.Seat> seatEntityResIterable = seatRepository.saveAll(SeatUtils.generateSeatsForScreen(screen, seatEntityList, seatRequest.getSeat().getScreenId()));
            List<com.bookmymovie.theater.entity.Seat> seatEntityResList = Streamable.of(seatEntityResIterable).toList();
            List<Seat> seatListRes = seatEntityResList.stream().map(Seat::new).toList();
            seatResponse.getSeats().addAll(seatListRes);
            statusMapper.mapSuccessCodeMsg(seatResponse);
        } catch(CoversionException ex) {
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            seatResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            seatResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            seatResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return seatResponse;
    }

    public SeatResponse updateSeatOperational(SeatRequest seatRequest) {
        SeatResponse seatResponse = new SeatResponse();
        try {
            com.bookmymovie.theater.entity.Seat seatEntityRes = seatRepository.findById(seatRequest.getSeat().getSeatId()).get();
            if(ObjectUtils.isEmpty(seatEntityRes)) {
                throw new RecordNotFoundException();
            }
            seatEntityRes.setOperational(seatRequest.getSeat().getOperational());
            com.bookmymovie.theater.entity.Seat seatResUpdated = seatRepository.save(seatEntityRes);
            seatResponse.getSeats().add(seatConverter.convertEntityToModel(seatResUpdated));
            statusMapper.mapSuccessCodeMsg(seatResponse);
        } catch(RecordNotFoundException ex) {
            log.error("RecordNotFoundException Occurs!");
            ex.printStackTrace();
            seatResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE));
        } catch(CoversionException ex) {
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            seatResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            seatResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            seatResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return seatResponse;
    }
}
