package com.bookmymovie.viewer.controllers;

import com.bookmymovie.viewer.model.Viewer;
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

    @PostMapping("/viewer-add")
    @ResponseBody
    public void addViewer(@RequestBody Viewer viewer) {
        viewerService.saveViewer(viewer);
    }

    @GetMapping("/viewer-by-mobile/{mobile}")
    @ResponseBody
    public Viewer getViewerByMobile(@PathVariable String mobile) {
        return viewerService.getViewerByMobile(mobile);
    }
}
