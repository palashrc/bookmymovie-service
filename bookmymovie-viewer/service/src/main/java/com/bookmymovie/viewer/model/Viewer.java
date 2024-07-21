package com.bookmymovie.viewer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Viewer {

    private Long viewerId;

    private String firstName;

    private String lastName;

    private String mobile;

    private String email;

    private String dob;

    private String anniversary;

    private String gender;

    private String state;

    private String city;

    private String addressLine;

    private String pinCode;
}
