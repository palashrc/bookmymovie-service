package com.bookmymovie.cinema.repository;

import com.bookmymovie.cinema.entity.MovieShow;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "movieshow", path = "movieshow")
public interface MovieShowRepository extends DatastoreRepository<MovieShow, Long> {
}
