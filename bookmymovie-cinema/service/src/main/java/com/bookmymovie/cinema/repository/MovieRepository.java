package com.bookmymovie.cinema.repository;

import com.bookmymovie.cinema.entity.Movie;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "movie", path = "movie")
public interface MovieRepository extends DatastoreRepository<Movie, Long> {
}
