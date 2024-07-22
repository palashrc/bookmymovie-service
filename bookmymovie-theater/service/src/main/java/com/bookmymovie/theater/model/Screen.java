package com.bookmymovie.theater.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Screen {

    private Long screenId;

    private Long theaterId;

    private String screenName;

    private Boolean operational;
}
