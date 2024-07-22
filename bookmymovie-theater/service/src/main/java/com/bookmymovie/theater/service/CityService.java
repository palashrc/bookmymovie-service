package com.bookmymovie.theater.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.RecordNotFoundException;
import com.bookmymovie.theater.helper.Constants;
import com.bookmymovie.theater.helper.StatusMapper;
import com.bookmymovie.theater.converter.CityConverter;
import com.bookmymovie.theater.model.City;
import com.bookmymovie.theater.model.CityRequest;
import com.bookmymovie.theater.model.CityResponse;
import com.bookmymovie.theater.repository.CityRepository;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CityService {

    @Autowired
    private CityConverter cityConverter;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StatusMapper statusMapper;

    public CityResponse saveCity(CityRequest cityRequest) {
        CityResponse cityResponse = new CityResponse();
        try {
            com.bookmymovie.theater.entity.City cityEntity = cityConverter.convertModelToEntity(cityRequest.getCity());
            com.bookmymovie.theater.entity.City cityEntityRes = cityRepository.save(cityEntity);
            cityResponse.getCities().add(cityConverter.convertEntityToModel(cityEntityRes));
            statusMapper.mapSuccessCodeMsg(cityResponse);
        } catch(CoversionException ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.EXCEPTION_TYPE));
        }
        return cityResponse;
    }

    public CityResponse getCity() {
        CityResponse cityResponse = new CityResponse();
        try {
            Iterable<com.bookmymovie.theater.entity.City> cityIterable = cityRepository.findAll();
            List<com.bookmymovie.theater.entity.City> cityList = Streamable.of(cityIterable).toList();
            List<City> cityListRes = cityList.stream().map(City::new).toList();
            cityResponse.getCities().addAll(cityListRes);
            statusMapper.mapSuccessCodeMsg(cityResponse);
        } catch(DatastoreException ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.EXCEPTION_TYPE));
        }
        return cityResponse;
    }

    public CityResponse getCityById(CityRequest cityRequest) {
        CityResponse cityResponse = new CityResponse();
        try {
            com.bookmymovie.theater.entity.City cityRes = cityRepository.findById(cityRequest.getCity().getCityId()).get();
            City city = cityConverter.convertEntityToModel(cityRes);
            cityResponse.getCities().add(city);
            statusMapper.mapSuccessCodeMsg(cityResponse);
        } catch(CoversionException ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.EXCEPTION_TYPE));
        }
        return cityResponse;
    }


    public CityResponse getCityByName(CityRequest cityRequest) {
        CityResponse cityResponse = new CityResponse();
        try {
            List<com.bookmymovie.theater.entity.City> cityList = cityRepository.findByCityName(cityRequest.getCity().getCityName()).get();
            if (!cityList.isEmpty()) {
                cityResponse.setCities(cityList.stream().map(City::new).collect(Collectors.toList()));
                statusMapper.mapSuccessCodeMsg(cityResponse);
            }
        } catch(DatastoreException ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.EXCEPTION_TYPE));
        }
        return cityResponse;
    }

    public CityResponse updateCityOperational(CityRequest cityRequest) {
        CityResponse cityResponse = new CityResponse();
        try {
            com.bookmymovie.theater.entity.City cityRes = cityRepository.findById(cityRequest.getCity().getCityId()).get();
            if(ObjectUtils.isEmpty(cityRes)) {
                throw new RecordNotFoundException();
            }
            cityRes.setOperational(cityRequest.getCity().getOperational());
            com.bookmymovie.theater.entity.City cityResUpdated = cityRepository.save(cityRes);
            cityResponse.getCities().add(cityConverter.convertEntityToModel(cityResUpdated));
            statusMapper.mapSuccessCodeMsg(cityResponse);
        } catch(RecordNotFoundException ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.RECORD_NOT_FOUND_EXCEPTION_TYPE));
        } catch(CoversionException ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(Constants.EXCEPTION_TYPE));
        }
        return cityResponse;
    }
}
