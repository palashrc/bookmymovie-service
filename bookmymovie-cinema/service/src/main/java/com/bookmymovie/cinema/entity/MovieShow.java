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

    @JsonProperty("theaterId")
    @Field(name = "TheaterId")
    private Long theaterId;

    @JsonProperty("screenId")
    @Field(name = "ScreenId")
    private Long screenId;

    @JsonProperty("screenAssigned")
    @Field(name = "ScreenAssigned")
    private List<String> screenAssigned;

    @JsonProperty("operational")
    @Field(name = "Operational")
    private Boolean operational;
}
