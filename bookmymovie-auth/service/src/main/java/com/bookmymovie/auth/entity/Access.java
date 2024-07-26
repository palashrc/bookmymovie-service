package com.bookmymovie.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import com.google.cloud.spring.data.datastore.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Access")
public class Access {

    @Id
    @JsonProperty("accessId")
    @Field(name = "AccessId")
    private Long accessId;

    @JsonProperty("accessKey")
    @Field(name = "AccessKey")
    private String accessKey;

    @JsonProperty("accessList")
    @Field(name = "AccessList")
    private List<String> accessList;
}
