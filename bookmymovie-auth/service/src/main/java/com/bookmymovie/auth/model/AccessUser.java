package com.bookmymovie.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessUser {

    private Long id;

    private Long userId;

    private String accessKey;

    private Long accessId;
}
