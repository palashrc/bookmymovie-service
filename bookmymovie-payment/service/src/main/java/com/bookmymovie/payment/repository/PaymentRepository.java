package com.bookmymovie.payment.repository;

import com.bookmymovie.payment.entity.Payment;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "payment", path = "payment")
public interface PaymentRepository extends DatastoreRepository<Payment, Long> {
}
