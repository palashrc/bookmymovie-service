package com.bookmymovie.theater.repository;

import com.bookmymovie.theater.entity.Theater;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(collectionResourceRel = "theater", path = "theater")
public interface TheaterRepository extends DatastoreRepository<Theater, Long> {

    Optional<List<Theater>> findByTheaterName(String theaterName);
}
