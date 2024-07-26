package com.bookmymovie.auth.service;

import com.bookmymovie.auth.converter.AccessConverter;
import com.bookmymovie.auth.model.AccessRequest;
import com.bookmymovie.auth.model.AccessUserRequest;
import com.bookmymovie.auth.model.AccessUserResponse;
import com.bookmymovie.auth.repository.AccessRepository;
import com.bookmymovie.auth.repository.AccessUserRepository;
import com.google.cloud.datastore.DatastoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private AccessConverter accessConverter;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private AccessUserRepository accessUserRepository;

    public void createAccess(AccessRequest accessRequest) {
        try {
            com.bookmymovie.auth.entity.Access roleEntity = accessConverter.convertModelToEntity(accessRequest.getAccess());
            accessRepository.save(roleEntity);
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
        }
    }

    public void updateAccess(AccessRequest accessRequest) {
        try {
            Optional<com.bookmymovie.auth.entity.Access> accessIdOp = accessRepository.findById(accessRequest.getAccess().getAccessId());
            if(accessIdOp.isPresent()) {
                com.bookmymovie.auth.entity.Access accessUpdateEntity = accessConverter.convertModelToEntity(accessRequest.getAccess());
                accessRepository.save(accessUpdateEntity);
            }
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
        }
    }

    public AccessUserResponse createAccessUser(AccessUserRequest accessUserRequest) {
        AccessUserResponse accessUserResponse = new AccessUserResponse();
        try {
            com.bookmymovie.auth.entity.Access a = accessRepository.findByAccessKey(accessUserRequest.getAccessUser().getAccessKey()).get();
            accessUserRequest.getAccessUser().setAccessId(a.getAccessId());
            com.bookmymovie.auth.entity.AccessUser accessUser = accessConverter.convertModelToEntity(accessUserRequest.getAccessUser());
            accessUserRepository.save(accessUser);
            accessUserResponse.setRegistrationStatus(Boolean.TRUE);
        } catch(DatastoreException ex) {
            log.error("DatastoreException Occurs!");
            ex.printStackTrace();
            accessUserResponse.setRegistrationStatus(Boolean.FALSE);
        } catch(Exception ex) {
            log.error("Exception Occurs!");
            ex.printStackTrace();
            accessUserResponse.setRegistrationStatus(Boolean.FALSE);
        }
        return accessUserResponse;
    }
}
