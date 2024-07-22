package com.bookmymovie.theater.service;

import com.bookmymovie.theater.model.City;
import com.bookmymovie.theater.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public void saveCity(City city) {
        com.bookmymovie.theater.entity.City cityEntity = new com.bookmymovie.theater.entity.City();
        cityEntity.setCityName(city.getCityName());
        cityEntity.setCityCode(city.getCityCode());
        cityEntity.setOperational(city.getOperational());
        cityRepository.save(cityEntity);
    }

    public List<City> getCity() {
        Iterable<com.bookmymovie.theater.entity.City> cityIterable = cityRepository.findAll();
        List<com.bookmymovie.theater.entity.City> cityList = Streamable.of(cityIterable).toList();
        List<City> cityListRes = cityList.stream().map(City::new).collect(Collectors.toList());
        return Streamable.of(cityListRes).toList();
    }

    public City getCityById(Long id) {
        City city = new City();
        if (cityRepository.findById(id).isPresent()) {
            com.bookmymovie.theater.entity.City cityRes = cityRepository.findById(id).get();
            city.setCityId(cityRes.getCityId());
            city.setCityName(cityRes.getCityName());
            city.setCityCode(cityRes.getCityCode());
            city.setOperational(cityRes.getOperational());
        }
        return city;
    }

    public List<City> getCityByName(String name) {
        List<City> cityListRes = new ArrayList<>();
        if (cityRepository.findByCityName(name).isPresent()) {
            List<com.bookmymovie.theater.entity.City> cityList = cityRepository.findByCityName(name).get();
            if (!cityList.isEmpty()) {
                cityListRes = cityList.stream().map(City::new).collect(Collectors.toList());
            }
        }
        return cityListRes;
    }
}
