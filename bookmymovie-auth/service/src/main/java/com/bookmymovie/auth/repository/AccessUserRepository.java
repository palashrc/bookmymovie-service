package com.bookmymovie.auth.repository;

import com.bookmymovie.auth.entity.AccessUser;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "accessuser", path = "accessuser")
public interface AccessUserRepository extends DatastoreRepository<AccessUser, Long> {
}
