package com.bookmymovie.theater.repository;

import com.bookmymovie.theater.entity.City;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(collectionResourceRel = "city", path = "city")
public interface CityRepository extends DatastoreRepository<City, Long> {

    Optional<List<City>> findByCityName(String cityName);
}
