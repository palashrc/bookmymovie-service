package com.bookmymovie.theater.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import com.google.cloud.spring.data.datastore.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Theater")
public class Theater {

    @Id
    @JsonProperty("theaterId")
    @Field(name = "TheaterId")
    private Long theaterId;

    @JsonProperty("cityId")
    @Field(name = "CityId")
    private Long cityId;

    @JsonProperty("theaterName")
    @Field(name = "TheaterName")
    private String theaterName;

    @JsonProperty("addressLine")
    @Field(name = "AddressLine")
    private String addressLine;

    @JsonProperty("availableFacilities")
    @Field(name = "AvailableFacilities")
    private List<String> availableFacilities;

    @JsonProperty("operational")
    @Field(name = "Operational")
    private Boolean operational;
}
