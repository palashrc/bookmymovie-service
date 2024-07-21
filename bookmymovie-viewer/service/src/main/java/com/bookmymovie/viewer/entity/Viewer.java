package com.bookmymovie.viewer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import com.google.cloud.spring.data.datastore.core.mapping.Field;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Viewer")
public class Viewer {

    @Id
    @JsonProperty("viewerId")
    @Field(name = "ViewerId")
    private Long viewerId;

    @JsonProperty("firstName")
    @Field(name = "FirstName")
    private String firstName;

    @JsonProperty("lastName")
    @Field(name = "LastName")
    private String lastName;

    @JsonProperty("mobile")
    @Field(name = "Mobile")
    private String mobile;

    @JsonProperty("email")
    @Field(name = "Email")
    private String email;

    @JsonProperty("dob")
    @Field(name = "Dob")
    private String dob;

    @JsonProperty("anniversary")
    @Field(name = "Anniversary")
    private String anniversary;

    @JsonProperty("gender")
    @Field(name = "Gender")
    private String gender;

    @JsonProperty("state")
    @Field(name = "State")
    private String state;

    @JsonProperty("city")
    @Field(name = "City")
    private String city;

    @JsonProperty("addressLine")
    @Field(name = "AddressLine")
    private String addressLine;

    @JsonProperty("pinCode")
    @Field(name = "PinCode")
    private String pinCode;
}
