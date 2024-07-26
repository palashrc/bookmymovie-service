package com.bookmymovie.auth.repository;

import com.bookmymovie.auth.entity.Access;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource(collectionResourceRel = "access", path = "access")
public interface AccessRepository extends DatastoreRepository<Access, Long> {
    Optional<Access> findByAccessKey(String accessKey);
}
