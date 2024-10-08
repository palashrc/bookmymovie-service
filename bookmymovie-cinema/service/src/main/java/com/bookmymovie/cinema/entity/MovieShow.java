package com.bookmymovie.cinema.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MovieShow {

    @Id
    @JsonProperty("movieShowId")
    @Field(name = "MovieShowId")
    private Long movieShowId;

    @JsonProperty("movieId")
    @Field(name = "MovieId")
    private Long movieId;

    @JsonProperty("cityId")
    @Field(name = "CityId")
    private Long cityId;

    @JsonProperty("theaterId")
    @Field(name = "TheaterId")
    private Long theaterId;

    @JsonProperty("theaterName")
    @Field(name = "TheaterName")
    private String theaterName;

    @JsonProperty("screenId")
    @Field(name = "ScreenId")
    private Long screenId;

    @JsonProperty("screenName")
    @Field(name = "ScreenName")
    private String screenName;

    @JsonProperty("showTimes")
    @Field(name = "ShowTimes")
    private List<String> showTimes;

    @JsonProperty("operational")
    @Field(name = "Operational")
    private Boolean operational;
}
