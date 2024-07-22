package com.bookmymovie.theater.repository;

import com.bookmymovie.theater.entity.Seat;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "seat", path = "seat")
public interface SeatRepository extends DatastoreRepository<Seat, Long> {
}
