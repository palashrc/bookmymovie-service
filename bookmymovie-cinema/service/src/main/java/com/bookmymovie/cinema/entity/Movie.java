package com.bookmymovie.cinema.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import com.google.cloud.spring.data.datastore.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Movie")
public class Movie {

    @Id
    @JsonProperty("movieId")
    @Field(name = "MovieId")
    private Long movieId;

    @JsonProperty("movieName")
    @Field(name = "MovieName")
    private String movieName;

    @JsonProperty("movieCategory")
    @Field(name = "MovieCategory")
    private String movieCategory;

    @JsonProperty("movieDescription")
    @Field(name = "MovieDescription")
    private String movieDescription;

    @JsonProperty("movieTrailerPath")
    @Field(name = "MovieTrailerPath")
    private String movieTrailerPath;

    @JsonProperty("movieCertificate")
    @Field(name = "MovieCertificate")
    private String movieCertificate;

    @JsonProperty("movieDuration")
    @Field(name = "MovieDuration")
    private String movieDuration;

    @JsonProperty("movieCasts")
    @Field(name = "MovieCasts")
    private List<String> movieCasts;

    @JsonProperty("movieCrews")
    @Field(name = "MovieCrews")
    private List<String> movieCrews;

    @JsonProperty("operational")
    @Field(name = "Operational")
    private Boolean operational;
}
