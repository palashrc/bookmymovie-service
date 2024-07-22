package com.bookmymovie.viewer.model;

import com.bookmymovie.core.error.Error;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewerResponse {

    private List<Viewer> viewers = new ArrayList<>();

    private String successCode;

    private String successMessage;

    private List<Error> errors = new ArrayList<>();
}
