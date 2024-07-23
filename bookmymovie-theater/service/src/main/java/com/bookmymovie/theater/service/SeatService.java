package com.bookmymovie.theater.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.theater.converter.ScreenConverter;
import com.bookmymovie.theater.converter.SeatConverter;
import com.bookmymovie.theater.helper.Constants;
import com.bookmymovie.theater.helper.StatusMapper;
import com.bookmymovie.theater.model.*;
import com.bookmymovie.theater.repository.ScreenRepository;
import com.bookmymovie.theater.repository.SeatRepository;
import com.bookmymovie.theater.util.SeatUtils;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
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
            seatResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            seatResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            seatResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.EXCEPTION_TYPE));
        }
        return seatResponse;
    }
}
