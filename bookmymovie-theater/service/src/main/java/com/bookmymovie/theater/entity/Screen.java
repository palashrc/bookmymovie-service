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
@Entity(name = "Screen")
public class Screen {

    @Id
    @JsonProperty("screenId")
    @Field(name = "ScreenId")
    private Long screenId;

    @JsonProperty("theaterId")
    @Field(name = "TheaterId")
    private Long theaterId;

    @JsonProperty("screenName")
    @Field(name = "ScreenName")
    private String screenName;

    @JsonProperty("operational")
    @Field(name = "Operational")
    private Boolean operational;
}
