package com.bookmymovie.theater.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Theater {

    private Long theaterId;

    private Long cityId;

    private String theaterName;

    private String addressLine;

    private List<String> availableFacilities;

    private Boolean operational;

    public Theater(com.bookmymovie.theater.entity.Theater theater){
        this.theaterId = theater.getTheaterId();
        this.cityId = theater.getCityId();
        this.theaterName = theater.getTheaterName();
        this.addressLine = theater.getAddressLine();
        this.availableFacilities = theater.getAvailableFacilities();
        this.operational = theater.getOperational();
    }
}
