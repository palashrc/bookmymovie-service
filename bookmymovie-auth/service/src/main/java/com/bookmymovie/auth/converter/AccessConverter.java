package com.bookmymovie.auth.converter;

import com.bookmymovie.auth.model.Access;
import com.bookmymovie.auth.model.AccessUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccessConverter {

    public com.bookmymovie.auth.entity.Access convertModelToEntity(Access access) {
        com.bookmymovie.auth.entity.Access accessEntity = new com.bookmymovie.auth.entity.Access();
        if(!ObjectUtils.isEmpty(access.getAccessId())) {
            accessEntity.setAccessId(access.getAccessId());
        }
        accessEntity.setAccessKey(access.getAccessKey());
        accessEntity.setAccessList(access.getAccessList());
        return accessEntity;
    }

    public com.bookmymovie.auth.entity.AccessUser convertModelToEntity(AccessUser accessUser) {
        com.bookmymovie.auth.entity.AccessUser accessUserEntity = new com.bookmymovie.auth.entity.AccessUser();
        accessUserEntity.setUserId(accessUser.getUserId());
        accessUserEntity.setAccessKey(accessUser.getAccessKey());
        accessUserEntity.setAccessId(accessUser.getAccessId());
        return accessUserEntity;
    }
}
