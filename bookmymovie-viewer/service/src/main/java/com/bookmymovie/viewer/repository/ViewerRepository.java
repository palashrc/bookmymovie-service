package com.bookmymovie.viewer.repository;

import com.bookmymovie.viewer.entity.Viewer;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RepositoryRestResource(collectionResourceRel = "viewer", path = "viewer")
public interface ViewerRepository extends DatastoreRepository<Viewer, Long> {

    Optional<Viewer> findByMobile(String mobile);
}
