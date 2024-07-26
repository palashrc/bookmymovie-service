package com.bookmymovie.viewer.controllers;

import com.bookmymovie.viewer.model.ViewerRequest;
import com.bookmymovie.viewer.model.ViewerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bookmymovie.viewer.service.ViewerService;

@RestController
@RequestMapping("/viewer")
@Slf4j
public class ViewerController {
    @Autowired
    private ViewerService viewerService;

    @PostMapping("/viewer-new")
    @ResponseBody
    public ViewerResponse addViewer(@RequestBody ViewerRequest viewerRequest) { return viewerService.createViewer(viewerRequest); }

    @PostMapping("/viewer-by-mobile")
    @ResponseBody
    public ViewerResponse getViewerByMobile(@RequestBody ViewerRequest viewerRequest) { return viewerService.getViewerByMobile(viewerRequest); }
}
