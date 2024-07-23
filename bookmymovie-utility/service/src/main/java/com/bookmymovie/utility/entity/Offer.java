package com.bookmymovie.utility.entity;

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
@Entity(name = "Offer")
public class Offer {

    @Id
    @JsonProperty("offerId")
    @Field(name = "OfferId")
    private Long offerId;

    @JsonProperty("offerDisplayName")
    @Field(name = "OfferDisplayName")
    private String offerDisplayName;

    @JsonProperty("offerCategory")
    @Field(name = "OfferCategory")
    private String offerCategory;

    @JsonProperty("offerApplicableCity")
    @Field(name = "OfferApplicableCity")
    private Long offerApplicableCity;

    @JsonProperty("offerApplicableTheater")
    @Field(name = "OfferApplicableTheater")
    private Long offerApplicableTheater;

    @JsonProperty("operational")
    @Field(name = "Operational")
    private Boolean operational;
}
