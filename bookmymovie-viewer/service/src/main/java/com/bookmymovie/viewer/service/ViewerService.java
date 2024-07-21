package com.bookmymovie.viewer.service;

import com.bookmymovie.viewer.model.Viewer;
import com.bookmymovie.viewer.repository.ViewerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ViewerService {

    @Autowired
    private ViewerRepository viewerRepository;

    public void saveViewer(Viewer viewer) {
        com.bookmymovie.viewer.entity.Viewer viewerEntity = new com.bookmymovie.viewer.entity.Viewer();
        viewerEntity.setFirstName(viewer.getFirstName());
        viewerEntity.setLastName(viewer.getLastName());
        viewerEntity.setMobile(viewer.getMobile());
        viewerEntity.setEmail(viewer.getEmail());
        viewerEntity.setDob(viewer.getDob());
        viewerEntity.setAnniversary(viewer.getAnniversary());
        viewerEntity.setGender(viewer.getGender());
        viewerEntity.setState(viewer.getState());
        viewerEntity.setCity(viewer.getCity());
        viewerEntity.setAddressLine(viewer.getAddressLine());
        viewerEntity.setPinCode(viewer.getPinCode());
        viewerRepository.save(viewerEntity);
    }

    public Viewer getViewerByMobile(String mobile) {
        Viewer viewer = new Viewer();
        if (viewerRepository.findByMobile(mobile).isPresent()) {
            com.bookmymovie.viewer.entity.Viewer viewerRes = viewerRepository.findByMobile(mobile).get();
            viewer.setViewerId(viewerRes.getViewerId());
            viewer.setFirstName(viewerRes.getFirstName());
            viewer.setLastName(viewerRes.getLastName());
            viewer.setMobile(mobile);
            viewer.setEmail(viewerRes.getEmail());
            viewer.setDob(viewerRes.getDob());
            viewer.setAnniversary(viewerRes.getAnniversary());
            viewer.setGender(viewerRes.getGender());
            viewer.setState(viewerRes.getState());
            viewer.setCity(viewerRes.getCity());
            viewer.setAddressLine(viewerRes.getAddressLine());
            viewer.setPinCode(viewerRes.getPinCode());
        }
        return viewer;
    }
}
