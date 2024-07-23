package com.bookmymovie.theater.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Screen {

    private Long screenId;

    private Long theaterId;

    private String screenName;

    private List<String> showTimes;

    private Integer totalRows;

    private Integer numberOfSeatsInEachRow;

    private List<String> rowNames;

    private Map<String, String> rowNameTypeMap;

    Map<String, String> rowTypePriceMap;

    private Boolean operational;
}
