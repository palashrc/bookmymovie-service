package com.bookmymovie.orchestrator.repository;

import com.bookmymovie.orchestrator.entity.OrchRequestTracker;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RepositoryRestResource(collectionResourceRel = "orchReqTracker", path = "orchReqTracker")
public interface OrchRequestTrackerRepository extends DatastoreRepository<OrchRequestTracker, Long> {

    Optional<OrchRequestTracker> findByTransactionId(String txnId);
}
