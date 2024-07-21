package com.bookmymovie.theater.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import com.google.cloud.spring.data.datastore.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "City")
public class City {

    @Id
    @JsonProperty("cityId")
    @Field(name = "CityId")
    private Long cityId;

    @JsonProperty("cityName")
    @Field(name = "CityName")
    private String cityName;

    @JsonProperty("cityCode")
    @Field(name = "CityCode")
    private String cityCode;
}
