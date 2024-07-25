package com.bookmymovie.order.repository;

import com.bookmymovie.order.entity.Order;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface OrderRepository extends DatastoreRepository<Order, Long> {
}
