package com.bookmymovie.auth.entity;

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
@Entity(name = "AccessUser")
public class AccessUser {

    @Id
    @JsonProperty("id")
    @Field(name = "Id")
    private Long id;

    @JsonProperty("userId")
    @Field(name = "UserId")
    private Long userId;

    @JsonProperty("accessKey")
    @Field(name = "AccessKey")
    private String accessKey;

    @JsonProperty("accessId")
    @Field(name = "AccessId")
    private Long accessId;
}
