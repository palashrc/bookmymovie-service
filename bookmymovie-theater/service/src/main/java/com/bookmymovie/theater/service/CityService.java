package com.bookmymovie.theater.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.RecordNotFoundException;
import com.bookmymovie.theater.constant.ExceptionConstants;
import com.bookmymovie.theater.helper.StatusMapper;
import com.bookmymovie.theater.converter.CityConverter;
import com.bookmymovie.theater.model.City;
import com.bookmymovie.theater.model.CityRequest;
import com.bookmymovie.theater.model.CityResponse;
import com.bookmymovie.theater.repository.CityRepository;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
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
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
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
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
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
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
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
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
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
            log.error("RecordNotFoundException Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE));
        } catch(CoversionException ex) {
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return cityResponse;
    }

    public CityResponse getCityForUser() {
        CityResponse cityResponse = new CityResponse();
        try {
            Iterable<com.bookmymovie.theater.entity.City> cityIterable = cityRepository.findAll();
            List<com.bookmymovie.theater.entity.City> cityList = Streamable.of(cityIterable).toList();
            List<City> cityListRes = cityList.stream().map(City::new).toList();

            List<City> cityListResForUser = cityListRes.stream()
                                                       .filter(c -> Objects.nonNull(c))
                                                       .filter(c -> Objects.nonNull(c.getOperational()))
                                                       .filter(c -> BooleanUtils.isTrue(c.getOperational()))
                                                       .collect(Collectors.toList());
            cityResponse.getCities().addAll(cityListResForUser);
            statusMapper.mapSuccessCodeMsg(cityResponse);
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return cityResponse;
    }

    public CityResponse getCityByIdForUser(CityRequest cityRequest) {
        CityResponse cityResponse = new CityResponse();
        try {
            com.bookmymovie.theater.entity.City cityRes = cityRepository.findById(cityRequest.getCity().getCityId()).get();
            City city = cityConverter.convertEntityToModel(cityRes);
            if(BooleanUtils.isTrue(city.getOperational())) {
                cityResponse.getCities().add(city);
            }
            statusMapper.mapSuccessCodeMsg(cityResponse);
        } catch(CoversionException ex) {
            log.error("CoversionException Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE));
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return cityResponse;
    }

    public CityResponse getCityByNameForUser(CityRequest cityRequest) {
        CityResponse cityResponse = new CityResponse();
        try {
            List<com.bookmymovie.theater.entity.City> cityList = cityRepository.findByCityName(cityRequest.getCity().getCityName()).get();
            if (!cityList.isEmpty()) {
                List<City> cityModelList = cityList.stream().map(City::new).collect(Collectors.toList());
                List<City> cityListFilteredResForUser = cityModelList.stream()
                                                                   .filter(c -> Objects.nonNull(c))
                                                                   .filter(c -> Objects.nonNull(c.getOperational()))
                                                                   .filter(c -> BooleanUtils.isTrue(c.getOperational()))
                                                                   .collect(Collectors.toList());
                cityResponse.setCities(cityListFilteredResForUser);
                statusMapper.mapSuccessCodeMsg(cityResponse);
            }
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE));
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            cityResponse.getErrors().add(statusMapper.mapErrorCodeMsg(ExceptionConstants.EXCEPTION_TYPE));
        }
        return cityResponse;
    }
}
