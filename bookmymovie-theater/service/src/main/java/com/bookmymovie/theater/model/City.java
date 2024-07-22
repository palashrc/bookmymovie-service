package com.bookmymovie.theater.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {

    private Long cityId;

    private String cityName;

    private String cityCode;

    private Boolean operational;

    public City(com.bookmymovie.theater.entity.City city){
        this.cityId = city.getCityId();
        this.cityName = city.getCityName();
        this.cityCode = city.getCityCode();
        this.operational = city.getOperational();
    }
}
