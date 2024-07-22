package com.bookmymovie.theater.repository;

import com.bookmymovie.theater.entity.Screen;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "screen", path = "screen")
public interface ScreenRepository extends DatastoreRepository<Screen, Long> {
}
