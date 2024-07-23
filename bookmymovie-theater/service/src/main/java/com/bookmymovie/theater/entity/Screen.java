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
import java.util.Map;

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

    @JsonProperty("showTimes")
    @Field(name = "ShowTimes")
    private List<String> showTimes;

    @JsonProperty("totalRows")
    @Field(name = "TotalRows")
    private Integer totalRows;

    @JsonProperty("numberOfSeatsInEachRow")
    @Field(name = "NumberOfSeatsInEachRow")
    private Integer numberOfSeatsInEachRow;

    @JsonProperty("rowNames")
    @Field(name = "RowNames")
    private List<String> rowNames;

    @JsonProperty("rowNameTypeMap")
    @Field(name = "RowNameTypeMap")
    private Map<String, String> rowNameTypeMap;

    @JsonProperty("rowTypePriceMap")
    @Field(name = "RowTypePriceMap")
    Map<String, String> rowTypePriceMap;

    @JsonProperty("operational")
    @Field(name = "Operational")
    private Boolean operational;
}
