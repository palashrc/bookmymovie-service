package com.bookmymovie.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Access {

    private Long accessId;

    private String accessKey;

    private List<String> accessList;
}
