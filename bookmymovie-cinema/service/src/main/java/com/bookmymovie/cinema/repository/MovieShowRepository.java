package com.bookmymovie.cinema.repository;

import com.bookmymovie.cinema.entity.MovieShow;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(collectionResourceRel = "movieshow", path = "movieshow")
public interface MovieShowRepository extends DatastoreRepository<MovieShow, Long> {

    Optional<List<MovieShow>> findByMovieIdAndCityId(Long movieId, Long cityId);
}
